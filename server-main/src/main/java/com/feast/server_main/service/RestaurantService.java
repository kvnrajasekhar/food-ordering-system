package com.feast.server_main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feast.server_main.model.Restaurant;
import com.feast.server_main.repository.RestaurantRepository;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant updateRestaurant(Integer restaurantId, Restaurant restaurant) {
        restaurant.setResId(restaurantId);
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Integer restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
