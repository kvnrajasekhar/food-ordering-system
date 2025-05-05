package com.feast.server_main.dto;

import java.time.LocalDateTime;


public class CusOrderDTO {
	
    private Integer orderId;
    private ResFoodItemDTO resFoodItemTO;
    private Double totalPrice;
    private Integer quantity;
    private LocalDateTime date;
	public CusOrderDTO(Integer orderId, ResFoodItemDTO resFoodItemTO, Double totalPrice, Integer quantity,
			LocalDateTime date) {
		super();
		this.orderId = orderId;
		this.resFoodItemTO = resFoodItemTO;
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
	public ResFoodItemDTO getResFoodItemTO() {
		return resFoodItemTO;
	}
	public void setResFoodItemTO(ResFoodItemDTO resFoodItemTO) {
		this.resFoodItemTO = resFoodItemTO;
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
