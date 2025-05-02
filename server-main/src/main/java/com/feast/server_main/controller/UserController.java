package com.feast.server_main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feast.server_main.dto.FoodItemDTO;
import com.feast.server_main.dto.OrderDTO;
import com.feast.server_main.dto.RestaurantDTO;
import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.RestaurantOrderStatus;
import com.feast.server_main.service.UserService;

@RestController
@RequestMapping("/customer")
@CrossOrigin("http://127.0.0.1:5500")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/food-items")
    public List<FoodItemDTO> getAllFoodItems() {
        return (userService.getAllFoodItems());
    }

    @GetMapping("/restaurants")
    public List<RestaurantDTO> getAllRestaurants() {
        return userService.getAllRestaurants();
    }

    @GetMapping("/restaurants/{restaurantId}/food-items")
    public List<FoodItemDTO> getFoodItemsByRestaurant(@PathVariable Integer restaurantId) {
        return (userService.getFoodItemsByRestaurant(restaurantId));
    }

    @PostMapping("/order")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO placedOrder = userService.placeOrder(orderDTO);
            return ResponseEntity.ok(placedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/cart")
    public List<OrderDTO> getCartByOrderId(@RequestParam("userId") Integer userId) {
        return userService.getCartByUserId(userId);
    }

    @PutMapping("/cart/{orderId}")
    public ResponseEntity<String> updateOrder(
            @PathVariable Integer orderId,
            @RequestParam("userId") Integer userId,
            @RequestBody OrderDTO orderDTO) {
        try {
            int newQuantity = orderDTO.getQuantity();
            userService.updateOrderQuantity(orderId, newQuantity, userId);
            return ResponseEntity.ok("Quantity updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/cart/clear") 
    public ResponseEntity<String> clearCart(@RequestParam("userId") Integer userId) {
        try {
            userService.removeOrder(userId);
            return ResponseEntity.ok("Cart cleared successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile/{userId}")
    public List<UserDTO> getUserProfileByUserId(@PathVariable Integer userId) {
        return userService.getUserProfileByUserId(userId);
    }

    @PutMapping("/profile/edit/{userId}")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable Integer userId, @RequestBody UserDTO userDTO) {
        return userService.updateUserProfile(userId, userDTO);
    }
    
    @PostMapping("/order/status")
    public ResponseEntity<RestaurantOrderStatus> updateOrderStatus(@RequestBody RestaurantOrderStatus orderStatusDTO) {
            RestaurantOrderStatus savedOrderStatus = userService.updateOrderStatus(orderStatusDTO);
            return ResponseEntity.ok(savedOrderStatus);

    }
    
}
