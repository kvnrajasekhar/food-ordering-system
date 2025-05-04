package com.feast.server_main.dto;

import com.feast.server_main.model.User;

public class RestaurantDTO {
	
    private Integer restaurantId;
    private User user;
    private String restaurantName;
    private String address;
    private String cuisine;
    private String ownerName;

    public RestaurantDTO() {
    	
    }
	public RestaurantDTO(Integer restaurantId,User user, String restaurantName, String address, String cuisine, String ownerName) {
		super();
		this.restaurantId = restaurantId;
		this.user = user;
		this.restaurantName = restaurantName;
		this.address = address;
		this.cuisine = cuisine;
		this.ownerName = ownerName;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
    
   
}
