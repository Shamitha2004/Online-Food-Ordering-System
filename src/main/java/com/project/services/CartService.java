
package com.project.services;

import com.project.dtos.AddToCartRequest;
import com.project.dtos.UpdateCartItemRequest;
import com.project.entities.*;
import com.project.exceptions.ResourceNotFoundException;
import com.project.repositories.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import com.project.entities.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrdersRepository ordersRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       UserRepository userRepository,
                       MenuItemRepository menuItemRepository,
                       OrdersRepository ordersRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.ordersRepository = ordersRepository;
    }

    public Cart getOrCreateCart(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                logger.error("User not found: {}", userId);
                return new ResourceNotFoundException("User not found: " + userId);
            });
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            logger.info("Creating new cart for user: {}", userId);
            return cartRepository.save(new Cart(user));
        });
        logger.info("Retrieved cart for user: {}", userId);
        return cart;
    }

    @Transactional
    public Cart addItem(AddToCartRequest req) {
        try {
            if (req.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be > 0");
            }

            Cart cart = getOrCreateCart(req.getUserId());
            MenuItems item = menuItemRepository.findById(req.getMenuItemId())
                .orElseThrow(() -> {
                    logger.error("Menu item not found: {}", req.getMenuItemId());
                    return new ResourceNotFoundException("Menu item not found: " + req.getMenuItemId());
                });

            CartItem existing = cart.getItems().stream()
                .filter(ci -> ci.getMenuItem().getId().equals(item.getId()))
                .findFirst()
                .orElse(null);

            if (existing != null) {
                existing.setQuantity(existing.getQuantity() + req.getQuantity());
                logger.info("Increased quantity of item '{}' to {} in cart of user {}", 
                            item.getName(), existing.getQuantity(), req.getUserId());
            } else {
                CartItem ci = new CartItem(cart, item, req.getQuantity(), item.getPrice());
                cart.getItems().add(ci);
                logger.info("Added item '{}' x{} to cart of user {}", 
                            item.getName(), req.getQuantity(), req.getUserId());
            }

            return cartRepository.save(cart);
        } catch (Exception e) {
            logger.error("Error adding item to cart: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Cart updateItem(UpdateCartItemRequest req) {
        try {
            CartItem ci = cartItemRepository.findById(req.getCartItemId())
                .orElseThrow(() -> {
                    logger.error("Cart item not found: {}", req.getCartItemId());
                    return new ResourceNotFoundException("Cart item not found: " + req.getCartItemId());
                });

            if (req.getQuantity() <= 0) {
                Cart cart = ci.getCart();
                cart.getItems().remove(ci);
                cartItemRepository.delete(ci);
                logger.info("Removed cart item '{}' from cart of user {}", ci.getMenuItem().getName(), cart.getUser().getId());
                return cartRepository.save(cart);
            }

            ci.setQuantity(req.getQuantity());
            cartItemRepository.save(ci);
            logger.info("Updated quantity of item '{}' to {} in cart of user {}", 
                        ci.getMenuItem().getName(), req.getQuantity(), ci.getCart().getUser().getId());
            return ci.getCart();
        } catch (Exception e) {
            logger.error("Error updating cart item: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Cart removeItem(Long cartItemId) {
        try {
            CartItem ci = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> {
                    logger.error("Cart item not found: {}", cartItemId);
                    return new ResourceNotFoundException("Cart item not found: " + cartItemId);
                });
            Cart cart = ci.getCart();
            cart.getItems().remove(ci);
            cartItemRepository.delete(ci);
            logger.info("Removed item '{}' from cart of user {}", ci.getMenuItem().getName(), cart.getUser().getId());
            return cartRepository.save(cart);
        } catch (Exception e) {
            logger.error("Error removing cart item: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void clearCart(Long userId) {
        try {
            Cart cart = getOrCreateCart(userId);
            cart.getItems().clear();
            cartRepository.save(cart);
            logger.info("Cleared cart for user {}", userId);
        } catch (Exception e) {
            logger.error("Error clearing cart for user {}: {}", userId, e.getMessage());
            throw e;
        }
    }

    @Transactional
    public com.project.dtos.CheckoutResponse checkout(Long userId) {
        try {
            Cart cart = getOrCreateCart(userId);
            if (cart.getItems().isEmpty()) throw new IllegalStateException("Cart is empty");

            Orders order = new Orders();
            order.setUser(cart.getUser());
            order.setStatus(OrderStatus.PENDING);
            order.setOrderTime(LocalDateTime.now());
            order.setMenuItems(
                cart.getItems().stream().map(CartItem::getMenuItem).distinct().collect(Collectors.toList())
            );

            double total = cart.getItems().stream().mapToDouble(CartItem::getLineTotal).sum();
            Orders saved = ordersRepository.save(order);

            var itemNames = cart.getItems().stream()
                    .map(ci -> ci.getMenuItem().getName() + " x " + ci.getQuantity())
                    .collect(Collectors.toList());

            cart.clear();
            cartRepository.save(cart);

            logger.info("Checkout complete for user {}. Order id: {}. Total: {}", userId, saved.getId(), total);

            return new com.project.dtos.CheckoutResponse(saved.getId(), total, itemNames);
        } catch (Exception e) {
            logger.error("Error during checkout for user {}: {}", userId, e.getMessage());
            throw e;
        }
    }
}

