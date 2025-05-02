package com.feast.server_main.dto;

import com.feast.server_main.model.Restaurant;

public class UserDTO {

    private Integer userId;
    private String userName;
    private String email;
    private String password;
    private Long phoneNumber;
    private String address;
    private String role;
    private Restaurant restaurant;

    public UserDTO() {
    	
    }
	public UserDTO(Integer userId, String userName, String email,String password, Long phoneNumber, String address,
			String role,Restaurant restaurant) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
		this.restaurant = restaurant;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
    
}
