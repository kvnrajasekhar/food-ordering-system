package com.feast.server_main.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "food_items")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FoodId")
    private Long foodId;

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

    @OneToMany(mappedBy = "foodItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> carts;

    public FoodItem() {
    }

    public FoodItem(String foodName, String resName, String description, Float price, String imageURL) {
        this.foodName = foodName;
        this.resName = resName;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
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

    public List<CartItem> getCarts() {
        return carts;
    }

    public void setCarts(List<CartItem> carts) {
        this.carts = carts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodItem foodItem = (FoodItem) o;
        return Objects.equals(foodId, foodItem.foodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodId);
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", resName='" + resName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}