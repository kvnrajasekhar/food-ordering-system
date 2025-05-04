package com.feast.server_main.controller;

import java.util.List;
import java.util.Optional;

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

import com.feast.server_main.dto.FoodItemDTO;
import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.FoodItem;
import com.feast.server_main.model.RestaurantOrderStatus;
import com.feast.server_main.service.*;
import com.feast.server_main.response.StandardResponse; 

@RestController
@RequestMapping("/restaurant")
@CrossOrigin("http://127.0.0.1:5500")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

	@PostMapping("/create")
	public ResponseEntity<StandardResponse<UserDTO>> createRestaurant(
			@RequestParam("userId") Integer userId,
			@RequestParam("restaurantName") String restaurantName,
			@RequestParam("restaurantAddress") String restaurantAddress,
			@RequestParam("cuisine") String cuisine,
			@RequestParam("ownerName") String ownerName) {
		logger.info("Creating restaurant for userId: {}", userId);
		try {
			UserDTO userDTO = restaurantService.createRestaurantForExistingUser(userId, restaurantName, restaurantAddress,
					cuisine, ownerName);
			logger.info("Restaurant created successfully for userId: {}", userId);
			return new ResponseEntity<>(
					new StandardResponse<>(HttpStatus.CREATED.value(), "Restaurant created successfully", userDTO),
					HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			logger.error("Error creating restaurant: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (IllegalStateException e) {
			logger.error("Error creating restaurant: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			logger.error("Error creating restaurant: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create restaurant", e);
		}
	}

	@GetMapping("/check")
	public ResponseEntity<StandardResponse<HasRestaurantResponse>> hasRestaurant(@RequestParam("userId") Integer userId) {
		logger.info("Checking if user has restaurant. userId: {}", userId);
		try {
			boolean hasRestaurant = restaurantService.hasRestaurant(userId);
			HasRestaurantResponse response = new HasRestaurantResponse(hasRestaurant);
			logger.info("User has restaurant: {}, userId: {}", hasRestaurant, userId);
			return new ResponseEntity<>(
					new StandardResponse<>(HttpStatus.OK.value(), "Restaurant status checked", response), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error checking restaurant status: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to check restaurant status", e);
		}
	}

	// Inner class to represent the response
	static class HasRestaurantResponse {
		private boolean hasRestaurant;

		public HasRestaurantResponse(boolean hasRestaurant) {
			this.hasRestaurant = hasRestaurant;
		}

		public boolean isHasRestaurant() {
			return hasRestaurant;
		}
	}

	@GetMapping("/restaurantId")
	public ResponseEntity<StandardResponse<Integer>> getRestaurantId(@RequestParam("userId") Integer userId) {
		logger.info("Getting restaurant ID for userId: {}", userId);
		try {
			Optional<Integer> restaurantId = restaurantService.getRestaurantIdByUserId(userId);
			if (restaurantId.isPresent()) {
				logger.info("Restaurant ID found: {} for userId: {}", restaurantId.get(), userId);
				return new ResponseEntity<>(
						new StandardResponse<>(HttpStatus.OK.value(), "Restaurant ID retrieved successfully", restaurantId.get()),
						HttpStatus.OK);
			} else {
				logger.warn("Restaurant ID not found for userId: {}", userId);
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant ID not found");
			}
		} catch (Exception e) {
			logger.error("Error getting restaurant ID: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get restaurant ID", e);
		}
	}

	@PostMapping("/addItem")
	public ResponseEntity<StandardResponse<FoodItem>> addFoodItem(@RequestBody FoodItem foodItem) {
		logger.info("Adding food item: {}", foodItem.getFoodName());
		try {
			FoodItem item = restaurantService.addFoodItem(foodItem);
			logger.info("Food item added successfully: {}", item.getFoodName());
			return new ResponseEntity<>(
					new StandardResponse<>(HttpStatus.CREATED.value(), "Food item added successfully", item),
					HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error adding food item: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add food item", e);
		}
	}

	@GetMapping("/items")
	public ResponseEntity<StandardResponse<List<FoodItem>>> getAllItems() {
		logger.info("Getting all food items");
		try {
			List<FoodItem> items = restaurantService.getAllItems();
			logger.info("Retrieved {} food items", items.size());
			return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK.value(), "All food items retrieved", items),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error getting all food items: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve food items", e);
		}

	}

	@GetMapping("/{restaurantId}/food-items")
	public ResponseEntity<StandardResponse<List<FoodItemDTO>>> getFoodItemsByRestaurant(
			@PathVariable Integer restaurantId) {
		logger.info("Getting food items for restaurantId: {}", restaurantId);
		try {
			List<FoodItemDTO> foodItemDTOs = restaurantService.getFoodItemsByRestaurant(restaurantId);
			logger.info("Retrieved {} food items for restaurantId: {}", foodItemDTOs.size(), restaurantId);
			return new ResponseEntity<>(
					new StandardResponse<>(HttpStatus.OK.value(), "Food items retrieved successfully", foodItemDTOs),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error getting food items by restaurant: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Failed to retrieve food items by restaurant", e);
		}
	}

	@PutMapping("/food-item/{foodItemId}")
    public ResponseEntity<StandardResponse<FoodItemDTO>> updateFoodItem(
            @PathVariable Integer foodItemId,
            @RequestBody FoodItem foodItem) {
        logger.info("Updating food item with ID: {}", foodItemId);
        try {
            FoodItem updatedFoodItem = restaurantService.updateFoodItem(foodItemId, foodItem);
            FoodItemDTO foodItemDTO = restaurantService.convertToDto(updatedFoodItem);
            logger.info("Food item updated successfully: {}", updatedFoodItem.getFoodName());
            return new ResponseEntity<>(
                    new StandardResponse<>(HttpStatus.OK.value(), "Food item updated", foodItemDTO),
                    HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.warn("Food item not found for update. ID: {}", foodItemId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food item not found", e);
        }
         catch (Exception e) {
            logger.error("Error updating food item: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update food item", e);
        }
    }

    @DeleteMapping("/food-item/{foodItemId}")
    public ResponseEntity<StandardResponse<Void>> deleteFoodItem(@PathVariable Integer foodItemId) {
        logger.info("Deleting food item with ID: {}", foodItemId);
        try {
        	restaurantService.deleteFoodItem(foodItemId);
            logger.info("Food item deleted successfully. ID: {}", foodItemId);
            return new ResponseEntity<>(
                    new StandardResponse<>(HttpStatus.NO_CONTENT.value(), "Food item deleted successfully", null),
                    HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            logger.warn("Food item not found for deletion. ID: {}", foodItemId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food item not found", e);
        }catch (Exception e) {
            logger.error("Error deleting food item: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete food item", e);
        }
    }

	// Get order status by order ID
	@GetMapping("/order/{orderId}/status")
	public ResponseEntity<StandardResponse<RestaurantOrderStatus>> getOrderStatusByOrderId(
			@PathVariable Integer orderId) {
		logger.info("Getting order status for order ID: {}", orderId);
		try {
			RestaurantOrderStatus orderStatus = restaurantService.getOrderStatusByOrderId(orderId);
			if (orderStatus != null) {
				logger.info("Order status found for order ID {}: {}", orderId, orderStatus.getStatus());
				return new ResponseEntity<>(
						new StandardResponse<>(HttpStatus.OK.value(), "Order status retrieved", orderStatus), HttpStatus.OK);
			} else {
				logger.warn("Order status not found for order ID: {}", orderId);
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order status not found");
			}
		} catch (Exception e) {
			logger.error("Error getting order status: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve order status", e);
		}
	}

	@GetMapping("/order/{restaurantId}/statuses")
	public ResponseEntity<StandardResponse<List<RestaurantOrderStatus>>> getAllOrderStatusesByRestaurantId(
			@PathVariable Integer restaurantId) {
		logger.info("Getting all order statuses for restaurant ID: {}", restaurantId);
		try {
			List<RestaurantOrderStatus> orderStatuses = restaurantService
					.getAllOrderStatusesByRestaurantId(restaurantId);
			logger.info("Retrieved {} order statuses for restaurant ID: {}", orderStatuses.size(), restaurantId);
			return new ResponseEntity<>(
					new StandardResponse<>(HttpStatus.OK.value(), "Order statuses retrieved", orderStatuses), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error getting order statuses: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Failed to retrieve order statuses", e);
		}
	}

	@PutMapping("/order/status")
	public ResponseEntity<StandardResponse<RestaurantOrderStatus>> updateOrderStatus(
			@RequestBody RestaurantOrderStatus orderStatus) {
		logger.info("Updating order status for order ID: {}", orderStatus.getOrder().getOrderId());
		try {
			RestaurantOrderStatus updatedOrderStatus = restaurantService.updateOrderStatus(orderStatus);
			logger.info("Order status updated successfully for order ID: {}", orderStatus.getOrder().getOrderId());
			return new ResponseEntity<>(
					new StandardResponse<>(HttpStatus.OK.value(), "Order status updated", updatedOrderStatus), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error updating order status: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update order status", e);
		}
	}

	// Get all order status
	@GetMapping("/order/status/all")
	public ResponseEntity<StandardResponse<List<RestaurantOrderStatus>>> getAllOrderStatus() {
		logger.info("Getting all order statuses");
		try {
			List<RestaurantOrderStatus> allOrderStatus = restaurantService.getAllOrderStatus();
			logger.info("Retrieved {} order statuses in total.", allOrderStatus.size());
			return new ResponseEntity<>(
					new StandardResponse<>(HttpStatus.OK.value(), "All order statuses retrieved", allOrderStatus),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error getting all order statuses: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve all order statuses",
					e);
		}
	}
}

