package com.feast.server_main.repository;

import com.feast.server_main.model.Restaurant;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Query("SELECT count(r) > 0 FROM Restaurant r WHERE r.user.userId = ?1")
    boolean existsByUserId(Integer userId);
    Restaurant findByRestaurantId(Integer restaurantId);
	Optional<Restaurant> findByUser_UserId(Integer userId);
}