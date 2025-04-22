package com.feast.server_main.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "order_status")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrderId")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "ResId", nullable = false)
    private Restaurant restaurant;

    @Column(name = "TotalPrice")
    private Float totalPrice;

    @Column(name = "OrderDate")
    private LocalDateTime date;

    @Column(name = "Status", columnDefinition = "VARCHAR(255)")
    private String status;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Delivery delivery;

    public Order() {
    }

    public Order(User customer, Restaurant restaurant, Float totalPrice, LocalDateTime date, String status) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.totalPrice = totalPrice;
        this.date = date;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customer=" + (customer != null ? customer.getCustomerId() : null) +
                ", restaurant=" + (restaurant != null ? restaurant.getResId() : null) +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}