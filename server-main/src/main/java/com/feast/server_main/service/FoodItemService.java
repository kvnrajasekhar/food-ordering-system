package com.feast.server_main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feast.server_main.model.FoodItem;
import com.feast.server_main.repository.FoodItemRepository;

@Service
public class FoodItemService {
    @Autowired
    private FoodItemRepository foodItemRepository;

    public FoodItem addFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public List<FoodItem> getFoodItemsByRestaurant(Integer restaurantId) {
        return foodItemRepository.findByRestaurantRestaurantId(restaurantId);
    }

    public FoodItem updateFoodItem(Integer foodId, FoodItem foodItem) {
        foodItem.setFoodId(foodId);
        return foodItemRepository.save(foodItem);
    }

    public void deleteFoodItem(Integer foodId) {
        foodItemRepository.deleteById(foodId);
    }
}