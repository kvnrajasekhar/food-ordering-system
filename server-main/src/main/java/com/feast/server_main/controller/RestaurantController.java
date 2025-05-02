package com.feast.server_main.controller;

import java.util.List;
import java.util.Optional;

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
import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.RestaurantOrderStatus;
import com.feast.server_main.service.*;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin("http://127.0.0.1:5500")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createRestaurant( // Change the return type
            @RequestParam("userId") Integer userId,
            @RequestParam("restaurantName") String restaurantName,
            @RequestParam("restaurantAddress") String restaurantAddress,
            @RequestParam("cuisine") String cuisine,
            @RequestParam("ownerName") String ownerName) {
        
            UserDTO userDTO = restaurantService.createRestaurantForExistingUser(userId, restaurantName, restaurantAddress, cuisine, ownerName);
            return ResponseEntity.ok(userDTO); // Return the UserDTO
        
    }
    
    @GetMapping("/check")
    public ResponseEntity<HasRestaurantResponse> hasRestaurant(@RequestParam("userId") Integer userId) {
       boolean hasRestaurant = restaurantService.hasRestaurant(userId);
        return ResponseEntity.ok(new HasRestaurantResponse(hasRestaurant));
    }

    //  Inner class to represent the response
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
    public ResponseEntity<Integer> getRestaurantId(@RequestParam("userId") Integer userId) {
        Optional<Integer> restaurantId = restaurantService.getRestaurantIdByUserId(userId);
        if (restaurantId.isPresent()) {
            return ResponseEntity.ok(restaurantId.get());
        } else {
            return ResponseEntity.notFound().build(); //  Return 404 if no restaurant found
        }
    }
    
    
	@PostMapping("/addItem")
	public ResponseEntity<FoodItemDTO> addFoodItem(@RequestBody FoodItemDTO foodItemDTO) {
		FoodItemDTO item = restaurantService.addFoodItem(foodItemDTO);
		return ResponseEntity.ok(item);

	}

	@GetMapping("/items")
	public List<FoodItemDTO> getAllItems() {
		return restaurantService.getAllItems();
	}
	
	@GetMapping("/{restaurantId}/food-items")
    public List<FoodItemDTO> getFoodItemsByRestaurant(@PathVariable Integer restaurantId) {
        return (restaurantService.getFoodItemsByRestaurant(restaurantId));
    }

	@PutMapping("/food-item/{foodItemId}")
	public ResponseEntity<FoodItemDTO> updateFoodItem(@PathVariable Integer foodItemId,
			@RequestBody FoodItemDTO foodItemDTO) {
		FoodItemDTO updatedFoodItem = restaurantService.updateFoodItem(foodItemId, foodItemDTO);
		return ResponseEntity.ok(updatedFoodItem);
	}

	@DeleteMapping("/food-item/{foodItemId}")
	public ResponseEntity<Void> deleteFoodItem(@PathVariable Integer foodItemId) {
		restaurantService.deleteFoodItem(foodItemId);
		return ResponseEntity.noContent().build();
	}
	
	// Get order status by order ID
    @GetMapping("/order/{orderId}/status")
    public ResponseEntity<RestaurantOrderStatus> getOrderStatusByOrderId(@PathVariable Integer orderId) {
        RestaurantOrderStatus orderStatus = restaurantService.getOrderStatusByOrderId(orderId);
        if (orderStatus != null) {
            return ResponseEntity.ok(orderStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{restaurantId}/statuses")
    public ResponseEntity<List<RestaurantOrderStatus>> getAllOrderStatusesByRestaurantId(@PathVariable Integer restaurantId) {
        List<RestaurantOrderStatus> orderStatuses = restaurantService.getAllOrderStatusesByRestaurantId(restaurantId);
        return ResponseEntity.ok(orderStatuses);
    }

    @PutMapping("/order/status")
    public ResponseEntity<RestaurantOrderStatus> updateOrderStatus(@RequestBody RestaurantOrderStatus orderStatus) {
        RestaurantOrderStatus updatedOrderStatus = restaurantService.updateOrderStatus(orderStatus);
        return ResponseEntity.ok(updatedOrderStatus);
    }
    
     //Get all order status
    @GetMapping("/order/status/all")
    public ResponseEntity<List<RestaurantOrderStatus>> getAllOrderStatus() {
        List<RestaurantOrderStatus> allOrderStatus = restaurantService.getAllOrderStatus();
        return ResponseEntity.ok(allOrderStatus);
    }
	
	

}