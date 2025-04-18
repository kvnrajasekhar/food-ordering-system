package com.feast.server_main.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "food_items")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FoodId")
    private Integer foodId;

    @Column(name = "FoodName", columnDefinition = "VARCHAR(255)")
    private String foodName;

    @Column(name = "ResName", columnDefinition = "VARCHAR(255)")
    private String resName;

    @Column(name = "Description", columnDefinition = "VARCHAR(255)")
    private String description;

    @Column(name = "Price")
    private Float price;

    @Column(name = "ImageURL", columnDefinition = "VARCHAR(255)")
    private String imageURL;

    // Constructors
    public FoodItem() {
    }

    public FoodItem(String foodName, String resName, String description, Float price, String imageURL) {
        this.foodName = foodName;
        this.resName = resName;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
    }

    // Getters
    public Integer getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getResName() {
        return resName;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    // Setters
    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "FoodItems{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", resName='" + resName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodItem foodItems = (FoodItem) o;
        return Objects.equals(foodId, foodItems.foodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodId);
    }
}