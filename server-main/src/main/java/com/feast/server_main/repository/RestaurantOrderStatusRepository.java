package com.feast.server_main.repository;

import com.feast.server_main.dto.RestaurantOrderStatusDTO;
import com.feast.server_main.model.Restaurant;
import com.feast.server_main.model.RestaurantOrderStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOrderStatusRepository extends JpaRepository<RestaurantOrderStatus, Restaurant> {

	Optional<RestaurantOrderStatus> findByOrderOrderId(Integer orderId);

	List<RestaurantOrderStatus> findByRestaurantRestaurantId(Integer restaurantId);
    
}