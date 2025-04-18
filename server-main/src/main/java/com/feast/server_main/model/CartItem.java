package com.feast.server_main.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "carts")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartId")
    private Integer cartId;

    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "FoodId", nullable = false)
    private FoodItem foodItem;

    @Column(name = "Quantity")
    private Integer quantity;

    // Constructors
    public CartItem() {
    }

    public CartItem(Customer customer, FoodItem foodItem, Integer quantity) {
        this.customer = customer;
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    // Getters
    public Integer getCartId() {
        return cartId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Setters
    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", customer=" + (customer != null ? customer.getCustomerId() : null) +
                ", foodItem=" + (foodItem != null ? foodItem.getFoodId() : null) +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cart = (CartItem) o;
        return Objects.equals(cartId, cart.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId);
    }
}