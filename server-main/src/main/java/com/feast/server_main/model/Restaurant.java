package com.feast.server_main.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurants_generator")
    @SequenceGenerator(name = "restaurants_generator", sequenceName = "restaurants_sequence", allocationSize = 1)
    @Column(name = "RestaurantId")
    private Integer restaurantId;
    
    
    @OneToOne
    @JoinColumn(name = "UserId", unique = true) 
    private User user;

    @Column(name = "RestaurantName")
    private String restaurantName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Cuisine")
    private String cuisine;

    @Column(name = "OwnerName")
    private String ownerName;

    public Restaurant() {
    }

    public Restaurant(User user,Integer restaurantId, String restaurantName, String address, String cuisine, String ownerName) {
    	this.user = user;
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

    @Override
    public String toString() {
        return "Restaurant [restaurantId=" + restaurantId + ", restaurantName=" + restaurantName + ", address=" + address + ", cuisine=" + cuisine + ", ownerName=" + ownerName + "]";
    }
}