package com.feast.server_main.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResId")
    private Integer resId;

    @Column(name = "ResName", columnDefinition = "VARCHAR(255)")
    private String resName;

    @Column(name = "Address", columnDefinition = "VARCHAR(255)")
    private String address;

    @Column(name = "Cuisine", columnDefinition = "VARCHAR(255)")
    private String cuisine;

    @ManyToOne
    @JoinColumn(name = "ResOwner", nullable = false)
    private RestaurantOwner restaurantOwner;

    // Constructors
    public Restaurant() {
    }

    public Restaurant(String resName, String address, String cuisine, RestaurantOwner restaurantOwner) {
        this.resName = resName;
        this.address = address;
        this.cuisine = cuisine;
        this.restaurantOwner = restaurantOwner;
    }

    // Getters
    public Integer getResId() {
        return resId;
    }

    public String getResName() {
        return resName;
    }

    public String getAddress() {
        return address;
    }

    public String getCuisine() {
        return cuisine;
    }

    public RestaurantOwner getRestaurantOwner() {
        return restaurantOwner;
    }

    // Setters
    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "resId=" + resId +
                ", resName='" + resName + '\'' +
                ", address='" + address + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", restaurantOwner=" + (restaurantOwner != null ? restaurantOwner.getResOwnerId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(resId, that.resId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resId);
    }
}