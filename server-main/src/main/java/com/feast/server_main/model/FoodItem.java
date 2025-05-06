package com.feast.server_main.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FoodItems")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fooditems_generator")
    @SequenceGenerator(name = "fooditems_generator", sequenceName = "fooditems_sequence", allocationSize = 1)
    @Column(name = "FoodId")
    private Integer foodId;

    @Column(name = "FoodName", columnDefinition = "VARCHAR(255)")
    private String foodName;
    
    @Column(name = "FoodType", columnDefinition = "VARCHAR(255)")
    private String foodType;    

    @ManyToOne
    @JoinColumn(name = "RestaurantId")
    private Restaurant restaurant; 

    @Column(name = "Description", columnDefinition = "VARCHAR(255)")
    private String description;

    @Column(name = "Price")
    private Float price;

    @Column(name = "ImageURL", columnDefinition = "VARCHAR(255)")
    private String imageURL;

    @Column(name = "Rating")
    private Double rating; 

    public FoodItem() {
    }

    public FoodItem(Integer foodId, String foodName, String foodType, Restaurant restaurant, String description, Float price, String imageURL, Double rating) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodType= foodType;
        this.restaurant = restaurant;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.rating = rating;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "FoodItem [foodId=" + foodId + ", foodName=" + foodName + ", restaurant=" + restaurant + ", description=" + description + ", price=" + price + ", imageURL=" + imageURL + ", rating=" + rating + "]";
    }
}