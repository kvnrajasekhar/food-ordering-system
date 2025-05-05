package com.feast.server_main.dto;


public class RestaurantDTO {
	
    private Integer restaurantId;
    private String restaurantName;
    private String address;
    private String cuisine;
    private String ownerName;

    public RestaurantDTO() {
    	
    }
	public RestaurantDTO(Integer restaurantId, String restaurantName, String address, String cuisine, String ownerName) {
		super();
		this.restaurantId = restaurantId;
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
