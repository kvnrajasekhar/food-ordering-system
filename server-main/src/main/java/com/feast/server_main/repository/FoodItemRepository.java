package com.feast.server_main.repository;

import com.feast.server_main.model.FoodItem;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
	List<FoodItem> findByRestaurant_RestaurantId(Integer restaurantId);

}