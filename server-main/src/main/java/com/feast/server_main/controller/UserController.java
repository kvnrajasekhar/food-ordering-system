package com.feast.server_main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import com.feast.server_main.dto.CusOrderDTO;
import com.feast.server_main.dto.FoodItemDTO;
import com.feast.server_main.dto.OrderDTO;
import com.feast.server_main.dto.ResFoodItemDTO;
import com.feast.server_main.dto.RestaurantDTO;
import com.feast.server_main.dto.RestaurantOrderStatusDTO;
import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.Order;
import com.feast.server_main.model.RestaurantOrderStatus;
import com.feast.server_main.response.StandardResponse;
import com.feast.server_main.service.UserService;

@RestController
@RequestMapping("/customer")
@CrossOrigin("http://127.0.0.1:5500")
public class UserController {

    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/food-items")
    public ResponseEntity<StandardResponse<List<ResFoodItemDTO>>> getAllFoodItems() {
        logger.info("Getting all food items for customer.");
        try {
            List<ResFoodItemDTO> foodItems = userService.getAllFoodItems();
            logger.info("Retrieved {} food items.", foodItems.size());
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "Success", foodItems), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving food items: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve food items", e);
        }
    }
    
    @GetMapping("/food-items/{foodItemId}/restaurant")
    public ResponseEntity<StandardResponse<Integer>> getRestaurantIdByFoodItemId(@PathVariable Integer foodItemId) {
        logger.info("Getting restaurant ID for food item ID: {}", foodItemId);
        try {
            Integer restaurantId = userService.getRestaurantIdByFoodItemId(foodItemId);
            logger.info("Retrieved restaurant ID: {} for food item ID: {}", restaurantId, foodItemId);
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "Success", restaurantId), HttpStatus.OK);
        } catch (IllegalArgumentException e) { 
            logger.warn("Food item with ID {} not found.", foodItemId);
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.NOT_FOUND.value(), "Food item not found", null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error retrieving restaurant ID for food item ID {}: {}", foodItemId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve restaurant ID", e);
        }
    }

    @GetMapping("/restaurants")
    public ResponseEntity<StandardResponse<List<RestaurantDTO>>> getAllRestaurants() {
        logger.info("Getting all restaurants for customer.");
        try {
            List<RestaurantDTO> restaurants = userService.getAllRestaurants();
            logger.info("Retrieved {} restaurants.", restaurants.size());
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "Success", restaurants), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving restaurants: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve restaurants", e);
        }
    }

    @GetMapping("/restaurants/{restaurantId}/food-items")
    public ResponseEntity<StandardResponse<List<ResFoodItemDTO>>> getFoodItemsByRestaurant(
            @PathVariable Integer restaurantId) {
        logger.info("Getting food items for restaurantId: {}", restaurantId);
        try {
            List<ResFoodItemDTO> foodItems = userService.getFoodItemsByRestaurant(restaurantId);
            logger.info("Retrieved {} food items for restaurantId: {}", foodItems.size(), restaurantId);
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "Success", foodItems), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for restaurantId: {}", restaurantId, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error retrieving food items for restaurantId {}: {}", restaurantId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve food items by restaurant", e);
        }
    }

    @PostMapping("/order")
    public ResponseEntity<StandardResponse<CusOrderDTO>> placeOrder(@RequestBody OrderDTO orderDTO) {
        logger.info("Placing order: {}", orderDTO);
        try {
            CusOrderDTO placedOrder = userService.placeOrder(orderDTO);
            logger.info("Order placed successfully. Order ID: {}", placedOrder.getOrderId());
            return new ResponseEntity<>(
                    new StandardResponse<>(HttpStatus.CREATED.value(), "Order placed successfully", placedOrder),
                    HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid order details: {}", orderDTO, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error placing order: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to place order", e);
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<StandardResponse<List<OrderDTO>>> getCartByOrderId(@RequestParam("userId") Integer userId) {
        logger.info("Getting cart for userId: {}", userId);
        try {
            List<OrderDTO> cart = userService.getCartByUserId(userId);
            logger.info("Retrieved cart for userId {}. Cart size: {}", userId, cart.size());
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "Success", cart), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid userId: {}", userId, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error retrieving cart for userId {}: {}", userId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve cart", e);
        }
    }

    @PutMapping("/cart/{orderId}")
    public ResponseEntity<StandardResponse<OrderDTO>> updateOrder(@PathVariable Integer orderId,
            @RequestParam("userId") Integer userId, @RequestBody OrderDTO orderDTO) {
        logger.info("Updating orderId: {} for userId: {} with orderDTO: {}", orderId, userId, orderDTO);
        try {
            OrderDTO orderUpdated = userService.updateOrderQuantity(orderId, orderDTO.getQuantity(), userId);
            logger.info("Order updated successfully. Order ID: {}", orderId);
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "Successfully updated", orderUpdated),
                    HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid order update request. orderId: {}, userId: {}, orderDTO: {}", orderId, userId, orderDTO, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating order. orderId: {}, userId: {}, orderDTO: {}", orderId, userId, orderDTO, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update order", e);
        }
    }

    @DeleteMapping("/cart/clear")
    public ResponseEntity<StandardResponse<Void>> clearCart(@RequestParam("userId") Integer userId) {
        logger.info("Clearing cart for userId: {}", userId);
        try {
            userService.removeOrder(userId);
            logger.info("Cart cleared successfully for userId: {}", userId);
            return new ResponseEntity<>(
                    new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Order deleted successfully", null),
                    HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid userId: {}", userId, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error clearing cart for userId: {}", userId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to clear cart", e);
        }
    }
    
    
    @DeleteMapping("/item/clear/{orderId}")
    public ResponseEntity<StandardResponse<Void>> removeOrderItem(@PathVariable Integer orderId, @RequestParam("userId") Integer userId) {
        logger.info("Removing order item with orderId: {} for userId: {}", orderId, userId);
        try {
            userService.removeOrderItem(orderId, userId);
            logger.info("Order item removed successfully. Order ID: {}", orderId);
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "Successfully removed item", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request to remove order item. orderId: {}, userId: {}", orderId, userId, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error removing order item. orderId: {}, userId: {}", orderId, userId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to remove order item", e);
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<StandardResponse<List<UserDTO>>> getUserProfileByUserId(@PathVariable Integer userId) {
        logger.info("Getting user profile for userId: {}", userId);
        try {
            List<UserDTO> userProfile = userService.getUserProfileByUserId(userId);
            logger.info("User profile retrieved successfully for userId: {}", userId);
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "User profile retrieved successfully", userProfile), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid userId: {}", userId, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error retrieving user profile for userId {}: {}", userId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get user profile", e);
        }
    }

    @PutMapping("/profile/edit/{userId}")
    public ResponseEntity<StandardResponse<UserDTO>> updateUserProfile(@PathVariable Integer userId, @RequestBody UserDTO userDTO) {
        logger.info("Updating user profile for userId: {} with userDTO: {}", userId, userDTO);
        try {
            UserDTO updatedUser = userService.updateUserProfile(userId, userDTO);
            logger.info("User profile updated successfully for userId: {}", userId);
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "User profile updated", updatedUser), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid user profile update request. userId: {}, userDTO: {}", userId, userDTO, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating user profile for userId {}: {}", userId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update user profile", e);
        }
    }

    @PostMapping("/order/status")
    public ResponseEntity<StandardResponse<RestaurantOrderStatusDTO>> updateOrderStatus(@RequestBody RestaurantOrderStatus orderStatusDTO) {
        logger.info("Updating order status: {}", orderStatusDTO);
        try {
            RestaurantOrderStatusDTO savedOrderStatus = userService.updateOrderStatus(orderStatusDTO);
            logger.info("Order status updated successfully. Order ID: {}", savedOrderStatus.getOrderDTO().getOrderId());
            return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "Order status updated", savedOrderStatus), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid order status update request: {}", orderStatusDTO, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating order status: {}", orderStatusDTO, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update order status", e);
        }
    }
}
