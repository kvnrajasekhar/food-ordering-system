package com.feast.server_main.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "carts")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Changed from AUTO to IDENTITY
    @Column(name = "CartId")
    private Integer cartId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer; // Changed field name to 'customer'

    @ManyToOne
    @JoinColumn(name = "FoodId", nullable = false)
    private FoodItem foodItem;


    @Column(name = "Quantity")
    private Integer quantity;

    // Constructors
    public CartItem() {
    }

    public CartItem(User customer, FoodItem foodItem, Integer quantity) {
        this.customer = customer;
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    // Getters
    public Integer getCartId() {
        return cartId;
    }

    public User getCustomer() { // Changed getter name
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

    public void setCustomer(User customer) { // Changed setter name
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
        return "CartItem [cartId=" + cartId + ", customer=" + customer + ", foodItem=" + foodItem + ", quantity="
                + quantity + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(cartId, cartItem.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId);
    }
}