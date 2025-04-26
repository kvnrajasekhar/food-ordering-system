package com.feast.server_main.dto;


public class RestaurantDTO {
	
    private Integer restaurantId;

    private String resName;

    private String address;

    private String cuisine;

    private String ownerName;

	public RestaurantDTO(Integer restaurantId, String resName, String address, String cuisine, String ownerName) {
		super();
		this.restaurantId = restaurantId;
		this.resName = resName;
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

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
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
