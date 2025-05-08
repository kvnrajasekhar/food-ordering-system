package com.feast.server_main.dto;

import java.time.LocalDateTime;

public class OrderDTO {

    private Integer orderId;
    private UserDTO user;
    private FoodItemDTO foodItem;
    private Double totalPrice;
    private Integer quantity;
    private LocalDateTime date;
    
	public OrderDTO(Integer orderId, UserDTO user, FoodItemDTO foodItem, Double totalPrice, Integer quantity,
			LocalDateTime date) {
		super();
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
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public FoodItemDTO getFoodItem() {
		return foodItem;
	}
	public void setFoodItem(FoodItemDTO foodItem) {
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
    
    
}
