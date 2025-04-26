package com.feast.server_main.dto;

import java.time.LocalDateTime;

import com.feast.server_main.model.FoodItem;
import com.feast.server_main.model.User;


public class OrderDTO {

    private Integer orderId;
    private User user;
    private FoodItem foodItem;
    private Double totalPrice;
    private String quantity;
    private LocalDateTime date;
	public OrderDTO(Integer orderId, User user, FoodItem foodItem, Double totalPrice, String quantity,
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
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
    
    
}
