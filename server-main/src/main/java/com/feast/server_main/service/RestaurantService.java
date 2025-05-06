package com.feast.server_main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feast.server_main.dto.FoodItemDTO;
import com.feast.server_main.dto.OrderDTO;
import com.feast.server_main.dto.ResFoodItemDTO;
import com.feast.server_main.dto.RestaurantDTO;
import com.feast.server_main.dto.RestaurantOrderStatusDTO;
import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.FoodItem;
import com.feast.server_main.model.Order;
import com.feast.server_main.model.Restaurant;
import com.feast.server_main.model.RestaurantOrderStatus;
import com.feast.server_main.model.User;
import com.feast.server_main.repository.FoodItemRepository;
import com.feast.server_main.repository.OrderRepository;
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
	
	@Autowired
	private OrderRepository orderRepository;

	
	@Transactional
	public UserDTO createRestaurantForExistingUser(Integer userId, String restaurantName, String restaurantAddress,
			String cuisine, String ownerName) {

		User user = userRepository.findByUserId(userId);
	
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

		return convertToUserDTO(user); 
	}

	private UserDTO convertToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(user.getUserName());
		userDTO.setEmail(user.getEmail());
		userDTO.setRole(user.getRole());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setAddress(user.getAddress());

		if (user.getRestaurant() != null) {
			Restaurant restaurant = new Restaurant();
			restaurant.setRestaurantId(user.getRestaurant().getRestaurantId());
			restaurant.setRestaurantName(user.getRestaurant().getRestaurantName());
			restaurant.setAddress(user.getRestaurant().getAddress());
			restaurant.setCuisine(user.getRestaurant().getCuisine()); 
			restaurant.setOwnerName(user.getRestaurant().getOwnerName());
		}
		return userDTO;
	}

	public boolean hasRestaurant(Integer userId) {
		return restaurantRepository.existsByUserId(userId);
	}

	public Optional<Integer> getRestaurantIdByUserId(Integer userId) {
		Optional<Restaurant> restaurant = restaurantRepository.findByUser_UserId(userId);
		return restaurant.map(Restaurant::getRestaurantId);
	}

	public List<FoodItemDTO> getAllItems() {
		List<FoodItem> items = foodItemRepository.findAll();
		return items.stream()
                .map(this::mapFoodItemToDTO)
                .collect(Collectors.toList());
	}

	public List<ResFoodItemDTO> getFoodItemsByRestaurant(Integer restaurantId) {
		List<FoodItem> foodItems = foodItemRepository.findByRestaurant_RestaurantId(restaurantId);
		return convertToResDto(foodItems);
	}

	@Transactional
	public FoodItemDTO addFoodItem(FoodItem foodItem) {
		FoodItem savedFoodItem = foodItemRepository.save(foodItem);
		return mapFoodItemToDTO(savedFoodItem);
	}

	@Transactional
	public FoodItemDTO updateFoodItem(Integer foodItemId, FoodItem foodItem) {
		FoodItem existingFoodItem = foodItemRepository.findById(foodItemId)
				.orElseThrow(() -> new IllegalArgumentException("Food Item not found with ID: " + foodItemId));

		existingFoodItem.setFoodName(foodItem.getFoodName());
		existingFoodItem.setFoodType(foodItem.getFoodType());
		existingFoodItem.setDescription(foodItem.getDescription());
		existingFoodItem.setPrice(foodItem.getPrice());
		existingFoodItem.setImageURL(foodItem.getImageURL());
		existingFoodItem.setRating(foodItem.getRating());

		return convertToDto(foodItemRepository.save(existingFoodItem));
	}

	public FoodItemDTO convertToDto(FoodItem foodItem) {
		return new FoodItemDTO(foodItem.getFoodId(), foodItem.getFoodName(), foodItem.getFoodType(),
				foodItem.getDescription(), foodItem.getPrice(), foodItem.getImageURL(), foodItem.getRating(),
				mapRestaurantToDTO(foodItem.getRestaurant()));
	}
	
	public ResFoodItemDTO mapToResFoodDto(FoodItem foodItem) {
		return new ResFoodItemDTO(foodItem.getFoodId(), foodItem.getFoodName(), foodItem.getFoodType(),
				foodItem.getDescription(), foodItem.getPrice(), foodItem.getImageURL(), foodItem.getRating()
				);
	}
	public ResFoodItemDTO mapToResFoodDto(FoodItemDTO foodItem) {
		return new ResFoodItemDTO(foodItem.getFoodId(), foodItem.getFoodName(), foodItem.getFoodType(),
				foodItem.getDescription(), foodItem.getPrice(), foodItem.getImageURL(), foodItem.getRating()
				);
	}
	
	private RestaurantDTO mapRestaurantToDTO(Restaurant restaurant) {
		return new RestaurantDTO(restaurant.getRestaurantId(), restaurant.getRestaurantName(), restaurant.getAddress(),
				restaurant.getCuisine(), restaurant.getOwnerName());
	}
	
	
	public List<ResFoodItemDTO> convertToResDto(List<FoodItem> foodItems) {
		List<ResFoodItemDTO> resFoodItems = new ArrayList<>();
		if (foodItems != null && !foodItems.isEmpty()) { 
			for (FoodItem foodItem : foodItems) {
				ResFoodItemDTO resFoodItem = new ResFoodItemDTO(
				foodItem.getFoodId(),
				foodItem.getFoodName(),
				foodItem.getFoodType(),
				foodItem.getDescription(),
				foodItem.getPrice(),
				foodItem.getImageURL(),
				foodItem.getRating()
						);
				resFoodItems.add(resFoodItem);
			}
		}
		return resFoodItems;
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

	public RestaurantOrderStatusDTO getOrderStatusByOrderId(Integer orderId) {
		Optional<RestaurantOrderStatus> orderStatus = restaurantOrderStatusRepository.findByOrderOrderId(orderId);
		return mapResOrderToDTO(orderStatus.orElse(null));
	}

	public List<RestaurantOrderStatusDTO> getAllOrderStatusesByRestaurantId(Integer restaurantId) {
		List<RestaurantOrderStatus> restaurantOrderList = restaurantOrderStatusRepository.findByRestaurantRestaurantId(restaurantId);
		return restaurantOrderList.stream()
                .map(this::mapResOrderToDTO)
                .collect(Collectors.toList());
	}

	@Transactional
    public RestaurantOrderStatusDTO updateOrderStatus(Integer orderId, String newStatus, Integer restaurantId) {
        Optional<RestaurantOrderStatus> existingOrderStatusOptional = restaurantOrderStatusRepository
                .findByOrderOrderId(orderId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with ID: " + restaurantId));

        Order order = orderRepository.findByOrderId(orderId);

        RestaurantOrderStatus orderStatusToSave;

        if (existingOrderStatusOptional.isPresent()) {
            RestaurantOrderStatus existingOrderStatus = existingOrderStatusOptional.get();
            existingOrderStatus.setRestaurant(restaurant);
            existingOrderStatus.setOrder(order);
            existingOrderStatus.setStatus(newStatus);
            orderStatusToSave = restaurantOrderStatusRepository.save(existingOrderStatus);
        } else {
            RestaurantOrderStatus newOrderStatus = new RestaurantOrderStatus();
            newOrderStatus.setRestaurant(restaurant);
            newOrderStatus.setOrder(order);
            newOrderStatus.setStatus(newStatus);
            orderStatusToSave = restaurantOrderStatusRepository.save(newOrderStatus);
        }

        return mapResOrderToDTO(orderStatusToSave);
    }
	
	public UserDTO convertUserToDto(User user) {
		return new UserDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhoneNumber(),
				user.getAddress(), user.getRole());
	}
	
	public FoodItemDTO mapFoodItemToDTO(FoodItem foodItem) {
		return new FoodItemDTO(foodItem.getFoodId(), foodItem.getFoodName(), foodItem.getFoodType(),
				foodItem.getDescription(), foodItem.getPrice(), foodItem.getImageURL(), foodItem.getRating(),
				mapRestaurantToDTO(foodItem.getRestaurant()));
	}
	
	private OrderDTO mapOrderToDTO(Order order,RestaurantOrderStatus entity) {
		return new OrderDTO(order.getOrderId(), convertUserToDto(entity.getOrder().getUser()), mapFoodItemToDTO(entity.getOrder().getFoodItem()), order.getTotalPrice(),
				order.getQuantity(), order.getDate());
	}
	
	private RestaurantOrderStatusDTO mapResOrderToDTO(RestaurantOrderStatus entity) {
		RestaurantOrderStatusDTO orderStatusDTO = new RestaurantOrderStatusDTO();
		orderStatusDTO.setRestaurantOrderStatusId(entity.getRestaurantOrderStatusId());
		orderStatusDTO.setRestaurantDTO(mapRestaurantToDTO(entity.getRestaurant()));
		orderStatusDTO.setOrderDTO(mapOrderToDTO(entity.getOrder(),entity));
		orderStatusDTO.setStatus(entity.getStatus());
		orderStatusDTO.setOrderedAt(entity.getOrderedAt());
		return orderStatusDTO;
	}
	
	

	public List<RestaurantOrderStatusDTO> getAllOrderStatus() {
		List<RestaurantOrderStatus> ordersList = restaurantOrderStatusRepository.findAll();
		return ordersList.stream()
                .map(this::mapResOrderToDTO)
                .collect(Collectors.toList());
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


	private FoodItemDTO mapFoodItemToDto(FoodItem foodItem) {
		return new FoodItemDTO(foodItem.getFoodId(), foodItem.getFoodName(), foodItem.getFoodType(),
				foodItem.getDescription(), foodItem.getPrice(), foodItem.getImageURL(), foodItem.getRating(),
				mapRestaurantToDTO(foodItem.getRestaurant()));
	}

}