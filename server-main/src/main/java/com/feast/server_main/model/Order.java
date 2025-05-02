package com.feast.server_main.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrderId")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "FoodId", nullable = false)
    private FoodItem foodItem;

    @Column(name = "TotalPrice")
    private Double totalPrice;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "OrderDate")
    private LocalDateTime date;

    public Order() {
    }

    public Order(Integer orderId, User user, FoodItem foodItem, Double totalPrice, Integer quantity, LocalDateTime date) {
        this.orderId = orderId;
        this.user = user;
        this.foodItem = foodItem;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.date = date;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", user=" + user + ", foodItem=" + foodItem + ", totalPrice=" + totalPrice + ", quantity=" + quantity + ", date=" + date + "]";
    }
}