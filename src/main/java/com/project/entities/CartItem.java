package com.project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItems menuItem;

    private int quantity;
    private double price; // unit price

    public CartItem() {}

    public CartItem(Cart cart, MenuItems menuItem, int quantity, double price) {
        this.cart = cart;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public MenuItems getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItems menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLineTotal() {
        return this.price * this.quantity;
    }
}
