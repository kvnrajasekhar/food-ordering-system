package com.feast.server_main.dto;

import java.time.LocalDateTime;

import com.feast.server_main.model.Order;
import com.feast.server_main.model.Restaurant;

public class RestaurantOrderStatusDTO {
	
	private Integer restaurantOrderStatusId;
    private Restaurant restaurant;
    private Order order;
    private String status;
    private LocalDateTime orderedAt;
    
	public RestaurantOrderStatusDTO(Integer restaurantOrderStatusId, Restaurant restaurant, Order order, String status,
			 LocalDateTime orderedAt) {
		super();
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
    
   
    
    
}
