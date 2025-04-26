package com.feast.server_main.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RestaurantId")
    private Integer restaurantId;

    @Column(name = "RestaurantName")
    private String resName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Cuisine")
    private String cuisine;

    @Column(name = "OwnerName")
    private String ownerName;

    public Restaurant() {
    }

    public Restaurant(Integer restaurantId, String resName, String address, String cuisine, String ownerName) {
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

    @Override
    public String toString() {
        return "Restaurant [restaurantId=" + restaurantId + ", resName=" + resName + ", address=" + address + ", cuisine=" + cuisine + ", ownerName=" + ownerName + "]";
    }
}