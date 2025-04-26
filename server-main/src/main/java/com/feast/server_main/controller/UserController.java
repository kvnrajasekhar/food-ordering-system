package com.feast.server_main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feast.server_main.dto.FoodItemDTO;
import com.feast.server_main.dto.OrderDTO;
import com.feast.server_main.dto.RestaurantDTO;
import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.service.UserService;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://127.0.0.1:5500") 
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
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
        OrderDTO placedOrder = userService.placeOrder(orderDTO);
        return ResponseEntity.ok(placedOrder);
    }
	
	@GetMapping("/cart")
	public List<OrderDTO> getCartByOrderId()
	{
		return userService.getCartByOrderId();
		
	}
	
	@GetMapping("/profile/{userName}")
	public List<UserDTO> getUserProfileByUsername(@PathVariable String userName) {
	    return userService.getUserProfileByUsername(userName);
	}
}
