package com.feast.server_main.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RestaurantOrderStatus")
public class RestaurantOrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RestaurantOrderStatusId")
    private Integer restaurantOrderStatusId;

    @ManyToOne
    @JoinColumn(name = "RestaurantId", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "OrderId", nullable = false)
    private Order order; 

    @Column(name = "Status", columnDefinition = "VARCHAR(255)")
    private String status;

    @Column(name = "OrderedAt")
    private LocalDateTime orderedAt;

    public RestaurantOrderStatus() {
    }

    public RestaurantOrderStatus(Integer restaurantOrderStatusId, Restaurant restaurant, Order order, String status, LocalDateTime orderedAt) {
        this.restaurantOrderStatusId = restaurantOrderStatusId;
        this.restaurant = restaurant;
        this.order = order;
        this.status = status;
        this.orderedAt = orderedAt;
    }

    public Integer getRestaurantOrderStatusId() {
        return restaurantOrderStatusId;
    }

    public void setRestaurantOrderStatusId(Integer restaurantOrderStatusId) {
        this.restaurantOrderStatusId = restaurantOrderStatusId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    @Override
    public String toString() {
        return "RestaurantOrderStatus [restaurantOrderStatusId=" + restaurantOrderStatusId + ", restaurant=" + restaurant + ", order=" + order + ", status=" + status + ", orderedAt=" + orderedAt + "]";
    }
}