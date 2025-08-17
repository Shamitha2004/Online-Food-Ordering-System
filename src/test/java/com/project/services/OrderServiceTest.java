package com.project.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.project.dtos.OrderRequestDTO;
import com.project.entities.MenuItems;
import com.project.entities.Orders;
import com.project.entities.OrderStatus;
import com.project.entities.User;
import com.project.exceptions.ResourceNotFoundException;
import com.project.repositories.MenuItemRepository;
import com.project.repositories.OrdersRepository;
import com.project.repositories.PaymentRepository;
import com.project.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder_Success() {
        User user = new User();
        user.setId(3L);
        when(userRepository.findById(3L)).thenReturn(Optional.of(user));

        MenuItems item = new MenuItems();
        item.setId(2L);
        when(menuItemRepository.findAllById(List.of(2L))).thenReturn(List.of(item));

        Orders order = new Orders();
        order.setId(1L);
        when(ordersRepository.save(any(Orders.class))).thenReturn(order);

        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setUserId(3L);
        dto.setMenuItemIds(List.of(2L));

        Orders savedOrder = orderService.placeOrder(dto);

        assertNotNull(savedOrder);
        assertEquals(1L, savedOrder.getId());
        verify(ordersRepository, times(1)).save(any(Orders.class));
    }

    @Test
    void testPlaceOrder_UserNotFound() {
        when(userRepository.findById(3L)).thenReturn(Optional.empty());
        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setUserId(3L);
        dto.setMenuItemIds(List.of(2L));

        assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(dto));
    }
}
