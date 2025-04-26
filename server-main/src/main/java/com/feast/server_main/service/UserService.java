package com.feast.server_main.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.feast.server_main.dto.FoodItemDTO;
import com.feast.server_main.dto.OrderDTO;
import com.feast.server_main.dto.RestaurantDTO;
import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.FoodItem;
import com.feast.server_main.model.Order;
import com.feast.server_main.model.Restaurant;
import com.feast.server_main.model.User;
import com.feast.server_main.repository.FoodItemRepository;
import com.feast.server_main.repository.OrderRepository;
import com.feast.server_main.repository.RestaurantRepository;
import com.feast.server_main.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
	
	@Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(this::mapRestaurantToDTO)
                .collect(Collectors.toList());
    }

    public List<FoodItemDTO> getFoodItemsByRestaurant(Integer restaurantId) {
        List<FoodItem> foodItems = foodItemRepository.findByRestaurant_RestaurantId(restaurantId);
        return foodItems.stream()
                .map(this::mapFoodItemToDTO)
                .collect(Collectors.toList());
    }
	
    
    @Transactional
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        if (orderDTO.getUser() == null || orderDTO.getFoodItem() == null || orderDTO.getQuantity() == null || orderDTO.getTotalPrice() == null) {
            throw new IllegalArgumentException("Order details are incomplete.");
        }

        // 2. Fetch the User and FoodItem entities from the database using the IDs present in the DTO
        User user = userRepository.findById(orderDTO.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        FoodItem foodItem = foodItemRepository.findById(orderDTO.getFoodItem().getFoodId())
                .orElseThrow(() -> new IllegalArgumentException("Food item not found."));

        // 3. Create a new Order entity and populate it with data from the DTO and fetched entities
        Order order = new Order();
        order.setUser(user);
        order.setFoodItem(foodItem);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setQuantity(orderDTO.getQuantity());
        order.setDate(LocalDateTime.now()); 

        Order savedOrder = orderRepository.save(order);

        return mapOrderToDTO(savedOrder);
    }
    
    public List<OrderDTO> getCartByOrderId() {
    	List<Order> cart = orderRepository.findAll();
        return cart.stream()
                .map(this::mapOrderToDTO)
                .collect(Collectors.toList());
    }
    
    
    @Transactional(readOnly = true)
    public List<UserDTO> getUserProfileByUsername(String username) {
        List<User> user = userRepository.findByUserName(username);
        return user.stream()
                   .map(this::mapUserToDTO)
                   .collect(Collectors.toList());
        
    }
	
    
    private RestaurantDTO mapRestaurantToDTO(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getRestaurantId(),
                restaurant.getResName(),
                restaurant.getAddress(),
                restaurant.getCuisine(),
                restaurant.getOwnerName()
        );
    }

    private FoodItemDTO mapFoodItemToDTO(FoodItem foodItem) {
        return new FoodItemDTO(
                foodItem.getFoodId(),
                foodItem.getFoodName(),
                foodItem.getRestaurant(),
                foodItem.getDescription(),
                foodItem.getPrice(),
                foodItem.getImageURL(),
                foodItem.getRating()       
        );
    }

    private OrderDTO mapOrderToDTO(Order order) {
        return new OrderDTO(
                order.getOrderId(),
                order.getUser(),
                order.getFoodItem(),
                order.getTotalPrice(),
                order.getQuantity(),
                order.getDate()
        );
    }
	
    
    private UserDTO mapUserToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getPhoneNumber(),
                null,
                user.getAddress(),
                user.getRole()
        );
    }
}
