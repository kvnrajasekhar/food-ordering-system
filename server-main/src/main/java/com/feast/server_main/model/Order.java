package com.feast.server_main.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "order_status")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "ResId", nullable = false)
    private Restaurant restaurant;

    @Column(name = "TotalPrice")
    private Float totalPrice;

    @Column(name = "Date", columnDefinition = "DATETIME")
    private LocalDateTime date;

    @Column(name = "Status", columnDefinition = "VARCHAR(255)")
    private String status;

    // Constructors
    public Order() {
    }

    public Order(Customer customer, Restaurant restaurant, Float totalPrice, LocalDateTime date, String status) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.totalPrice = totalPrice;
        this.date = date;
        this.status = status;
    }

    // Getters
    public Integer getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "orderId=" + orderId +
                ", customer=" + (customer != null ? customer.getCustomerId() : null) +
                ", restaurant=" + (restaurant != null ? restaurant.getResId() : null) +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order that = (Order) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}