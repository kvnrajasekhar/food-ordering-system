package com.feast.server_main.dto;

import java.time.LocalDateTime;

import com.feast.server_main.model.Order;
import com.feast.server_main.model.Restaurant;

public class RestaurantOrderStatusDTO {
	
	private Integer restaurantOrderStatusId;
    private RestaurantDTO restaurantDTO;
    private OrderDTO orderDTO;
    private String status;
    private LocalDateTime orderedAt;
    
    public RestaurantOrderStatusDTO() {
    	
    }

	public RestaurantOrderStatusDTO(Integer restaurantOrderStatusId, RestaurantDTO restaurantDTO, OrderDTO orderDTO,
			String status, LocalDateTime orderedAt) {
		super();
		this.restaurantOrderStatusId = restaurantOrderStatusId;
		this.restaurantDTO = restaurantDTO;
		this.orderDTO = orderDTO;
		this.status = status;
		this.orderedAt = orderedAt;
	}

	public Integer getRestaurantOrderStatusId() {
		return restaurantOrderStatusId;
	}

	public void setRestaurantOrderStatusId(Integer restaurantOrderStatusId) {
		this.restaurantOrderStatusId = restaurantOrderStatusId;
	}

	public RestaurantDTO getRestaurantDTO() {
		return restaurantDTO;
	}

	public void setRestaurantDTO(RestaurantDTO restaurantDTO) {
		this.restaurantDTO = restaurantDTO;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
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
