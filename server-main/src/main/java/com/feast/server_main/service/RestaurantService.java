package com.feast.server_main.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feast.server_main.dto.FoodItemDTO;
import com.feast.server_main.dto.RestaurantDTO;
import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.FoodItem;
import com.feast.server_main.model.Restaurant;
import com.feast.server_main.model.RestaurantOrderStatus;
import com.feast.server_main.model.User;
import com.feast.server_main.repository.FoodItemRepository;
import com.feast.server_main.repository.RestaurantOrderStatusRepository;
import com.feast.server_main.repository.RestaurantRepository;
import com.feast.server_main.repository.UserRepository;

@Service
public class RestaurantService {

	@Autowired
	private FoodItemRepository foodItemRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantOrderStatusRepository restaurantOrderStatusRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
    public UserDTO createRestaurantForExistingUser(
            Integer userId,
            String restaurantName,
            String restaurantAddress,
            String cuisine,
            String ownerName) {

        User user = userRepository.findByUserId(userId);
        // 2. Check if the user already has a restaurant.
        if (user.getRestaurant() != null) {
             throw new IllegalStateException("User with ID " + userId + " already has a restaurant.");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setUser(user);
        restaurant.setRestaurantName(restaurantName);
        restaurant.setAddress(restaurantAddress);
        restaurant.setCuisine(cuisine);
        restaurant.setOwnerName(ownerName);
        restaurantRepository.save(restaurant);

        user.setRestaurant(restaurant);
        userRepository.save(user);

        return convertToUserDTO(user); //  Return the DTO
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setAddress(user.getAddress());

        if (user.getRestaurant() != null) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setRestaurantId(user.getRestaurant().getRestaurantId());
            restaurantDTO.setRestaurantName(user.getRestaurant().getRestaurantName());
            restaurantDTO.setAddress(user.getRestaurant().getAddress());
            restaurantDTO.setCuisine(user.getRestaurant().getCuisine()); // Corrected
            restaurantDTO.setOwnerName(user.getRestaurant().getOwnerName());// Corrected
        }
        return userDTO;
    }

    public boolean hasRestaurant(Integer userId) {
        //  CHECKING RESTAURANT REPO
        return restaurantRepository.existsByUserId(userId);
    }
    
    public Optional<Integer> getRestaurantIdByUserId(Integer userId) {
        Optional<Restaurant> restaurant = restaurantRepository.findByUser_UserId(userId);
        return restaurant.map(Restaurant::getRestaurantId); // Extract restaurantId, or Optional.empty()
    }
    
	public List<FoodItemDTO> getAllItems() {
		List<FoodItem> items = foodItemRepository.findAll();
		return items.stream().map(this::mapFoodItemToDto).collect(Collectors.toList());
	}

	public List<FoodItemDTO> getFoodItemsByRestaurant(Integer restaurantId) {
		List<FoodItem> foodItems = foodItemRepository.findByRestaurant_RestaurantId(restaurantId);
		return foodItems.stream().map(this::mapFoodItemToDto).collect(Collectors.toList());
	}

	@Transactional
	public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {
		Restaurant restaurant = restaurantRepository.findByRestaurantId(foodItemDTO.getRestaurant().getRestaurantId());
		FoodItem foodItem = convertDtoToEntity(foodItemDTO, restaurant);
		FoodItem savedFoodItem = foodItemRepository.save(foodItem);
		return convertEntityToDto(savedFoodItem);
	}

	@Transactional
	public FoodItemDTO updateFoodItem(Integer foodItemId, FoodItemDTO fooditemDTO) {
		FoodItem foodItem = foodItemRepository.findById(foodItemId)
				.orElseThrow(() -> new IllegalArgumentException("Food Item not found with ID: " + foodItemId));

		Restaurant restaurant = restaurantRepository.findByRestaurantId(fooditemDTO.getRestaurant().getRestaurantId());

		foodItem.setFoodName(fooditemDTO.getFoodName());
		foodItem.setFoodType(fooditemDTO.getFoodType());
		foodItem.setRestaurant(restaurant);
		foodItem.setDescription(fooditemDTO.getDescription());
		foodItem.setPrice(fooditemDTO.getPrice());
		foodItem.setImageURL(fooditemDTO.getImageURL());
		foodItem.setRating(fooditemDTO.getRating());

		FoodItem updatedFoodItem = foodItemRepository.save(foodItem);
		return convertEntityToDto(updatedFoodItem);
	}

	@Transactional
	public void deleteFoodItem(Integer foodItemId) {
		FoodItem foodItem = foodItemRepository.findById(foodItemId)
				.orElseThrow(() -> new IllegalArgumentException("Food Item not found"));
		foodItemRepository.delete(foodItem);
	}

	FoodItem convertFoodDtoToEntity(FoodItemDTO dto, Restaurant restaurant) {
		FoodItem foodItem = new FoodItem(dto.getFoodId(), dto.getFoodName(), dto.getFoodType(), restaurant,
				dto.getDescription(), dto.getPrice(), dto.getImageURL(), dto.getRating());
		return foodItem;
	}

	public RestaurantOrderStatus getOrderStatusByOrderId(Integer orderId) {
		Optional<RestaurantOrderStatus> orderStatus = restaurantOrderStatusRepository.findByOrderOrderId(orderId); // Use
																													// the
																													// correct
																													// repository
																													// method
		return orderStatus.orElse(null);
	}

	public List<RestaurantOrderStatus> getAllOrderStatusesByRestaurantId(Integer restaurantId) {
		return restaurantOrderStatusRepository.findByRestaurantRestaurantId(restaurantId);
	}

	@Transactional
	public RestaurantOrderStatus updateOrderStatus(RestaurantOrderStatus orderStatus) {

		Optional<RestaurantOrderStatus> existingOrderStatus = restaurantOrderStatusRepository
				.findByOrderOrderId(orderStatus.getOrder().getOrderId());
		if (existingOrderStatus.isPresent()) {
			RestaurantOrderStatus existing = existingOrderStatus.get();
			existing.setStatus(orderStatus.getStatus());
			return restaurantOrderStatusRepository.save(existing);
		} else {
			return restaurantOrderStatusRepository.save(orderStatus);
		}

	}

	public List<RestaurantOrderStatus> getAllOrderStatus() {
		return restaurantOrderStatusRepository.findAll();
	}



	private FoodItemDTO convertFoodEntityToDto(FoodItem entity) {
		FoodItemDTO dto = new FoodItemDTO(entity.getFoodId(), entity.getFoodName(), entity.getFoodType(),
				entity.getRestaurant(), entity.getDescription(), entity.getPrice(), entity.getImageURL(),
				entity.getRating());
		return dto;
	}

	FoodItem convertDtoToEntity(FoodItemDTO dto, Restaurant restaurant) {
		FoodItem foodItem = new FoodItem();
		foodItem.setFoodName(dto.getFoodName());
		foodItem.setFoodType(dto.getFoodType());
		foodItem.setRestaurant(restaurant);
		foodItem.setDescription(dto.getDescription());
		foodItem.setPrice(dto.getPrice());
		foodItem.setImageURL(dto.getImageURL());
		foodItem.setRating(dto.getRating());
		return foodItem;
	}

	private FoodItemDTO convertEntityToDto(FoodItem entity) {
		FoodItemDTO dto = new FoodItemDTO(entity.getFoodId(), entity.getFoodName(), entity.getFoodType(),
				entity.getRestaurant(), entity.getDescription(), entity.getPrice(), entity.getImageURL(),
				entity.getRating());
		return dto;
	}

	private FoodItemDTO mapFoodItemToDto(FoodItem foodItem) {
		return new FoodItemDTO(foodItem.getFoodId(), foodItem.getFoodName(), foodItem.getFoodType(),
				foodItem.getRestaurant(), foodItem.getDescription(), foodItem.getPrice(), foodItem.getImageURL(),
				foodItem.getRating());
	}

}