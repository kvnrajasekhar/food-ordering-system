package com.feast.server_main.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "resName", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodItem> foodItems;

    public Restaurant() {
    }

    public Restaurant(String resName, String address, String cuisine, RestaurantOwner restaurantOwner) {
        this.resName = resName;
        this.address = address;
        this.cuisine = cuisine;
        this.restaurantOwner = restaurantOwner;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Integer restaurantId) {
        this.resId = restaurantId;
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

    public RestaurantOwner getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
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
}