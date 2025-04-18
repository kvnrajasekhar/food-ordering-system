package com.feast.server_main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "restaurant_owners")
public class RestaurantOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResOwnerId")
    private Integer resOwnerId;

    @NotNull
    @Column(name = "UserName", columnDefinition = "VARCHAR(255)", nullable = false)
    private String userName;

    @Column(name = "Password", columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "OwnerName", columnDefinition = "VARCHAR(255)")
    private String ownerName;

    @Column(name = "PhoneNumber", columnDefinition = "BIGINT")
    private Long phoneNumber;

    // Constructors
    public RestaurantOwner() {
    }

    public RestaurantOwner(String userName, String password, String ownerName, Long phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
    }

	public Integer getResOwnerId() {
		return resOwnerId;
	}

	public void setResOwnerId(Integer resOwnerId) {
		this.resOwnerId = resOwnerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "RestaurantOwner [resOwnerId=" + resOwnerId + ", userName=" + userName + ", password=" + password
				+ ", ownerName=" + ownerName + ", phoneNumber=" + phoneNumber + "]";
	}



}