package com.feast.server_main.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feast.server_main.dto.CusOrderDTO;
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
public class UserService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private FoodItemRepository foodItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestaurantOrderStatusRepository restaurantOrderStatusRepository;
	
	@Autowired
	private RestaurantService restaurantService;

	@Transactional(readOnly = true)
	public List<RestaurantDTO> getAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return restaurants.stream().map(this::mapRestaurantToDTO).collect(Collectors.toList());
	}

	public Integer getRestaurantIdByFoodItemId(Integer foodItemId) {

        Optional<FoodItem> foodItem = foodItemRepository.findById(foodItemId); 
        if (foodItem.isPresent()) {
            return foodItem.get().getRestaurant().getRestaurantId();
        } else {
            return null;
        }
    }
	public List<ResFoodItemDTO> getFoodItemsByRestaurant(Integer restaurantId) {
		List<FoodItem> foodItems = foodItemRepository.findByRestaurant_RestaurantId(restaurantId);
		return restaurantService.convertToResDto(foodItems);
	}

	@Transactional
	public CusOrderDTO placeOrder(OrderDTO orderDTO) {
		if (orderDTO.getFoodItem() == null || orderDTO.getQuantity() == null || orderDTO.getTotalPrice() == null) {
			throw new IllegalArgumentException("Order details are incomplete.");
		}

		User user;
		if (orderDTO.getUser() != null && orderDTO.getUser().getUserId() != null) {
			user = userRepository.findById(orderDTO.getUser().getUserId()).orElseThrow(
					() -> new IllegalArgumentException("User not found for userId: " + orderDTO.getUser().getUserId()));
		} else if (orderDTO.getUser() != null && orderDTO.getUser().getUserName() != null
				&& !orderDTO.getUser().getUserName().isEmpty()) {
			List<User> users = userRepository.findByUserName(orderDTO.getUser().getUserName());
			if (users.isEmpty()) {
				throw new IllegalArgumentException("User not found for userName: " + orderDTO.getUser().getUserName());
			}
			user = users.get(0);
		} else {
			throw new IllegalArgumentException("User information (userId or userName) is required.");
		}
		FoodItem foodItem = foodItemRepository.findById(orderDTO.getFoodItem().getFoodId())
				.orElseThrow(() -> new IllegalArgumentException("Food item not found."));

		Order order = new Order();
		order.setUser(user);
		order.setFoodItem(foodItem);
		order.setTotalPrice(orderDTO.getTotalPrice());
		order.setQuantity(orderDTO.getQuantity());
		order.setDate(LocalDateTime.now());

		Order savedOrder = orderRepository.save(order);

		return mapOrderToCusDTO(savedOrder);
	}

	public List<OrderDTO> getCartByUserId(Integer userId) {
		User user = userRepository.findByUserId(userId);
		List<Order> cart = orderRepository.findByUserWithFoodItemAndRestaurant(user);
		return cart.stream().map(this::mapOrderToDTO).collect(Collectors.toList());
	}

	public List<ResFoodItemDTO> getAllFoodItems() {
		List<FoodItem> allFoodItems = foodItemRepository.findAll();
	
		  return allFoodItems.stream().map(this::mapFoodItemToResDTO)
		  .collect(Collectors.toList());
		 
//		return allFoodItems.stream().map(this::mapFoodItemToDTO).collect(Collectors.toList());
	}

    @Transactional
    public OrderDTO updateOrderQuantity(Integer orderId, int newQuantity, Integer userId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        if (!order.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Order does not belong to this user");
        }
        order.setQuantity(newQuantity);
        order.setTotalPrice(order.getFoodItem().getPrice() * (double) newQuantity);
        Order updatedOrder = orderRepository.save(order);

        return mapOrderToDTO(updatedOrder);
    }

	@Transactional
	public void removeOrder(Integer userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new IllegalArgumentException("User not found.");
		}
		User user = userOptional.get();
		List<Order> orders = orderRepository.findByUser(user);
		if (orders.isEmpty()) {
			throw new IllegalArgumentException("Cart is already empty for this user.");
		}
		orderRepository.deleteAll(orders);
	}

	@Transactional(readOnly = true)
	public List<UserDTO> getUserProfileByUserId(Integer userId) {
		User user = userRepository.findByUserId(userId);
		if (user != null) {
			return Collections.singletonList(mapUserToDTO(user));
		} else {
			return Collections.emptyList();
		}
	}

	@Transactional
	public UserDTO updateUserProfile(Integer userId, UserDTO userDTO) {
		User user = userRepository.findByUserId(userId);
		User existingUser = user;

		if (userDTO.getUserName() != null) {
			existingUser.setUserName(userDTO.getUserName());
		}
		if (userDTO.getPhoneNumber() != null) {
			existingUser.setPhoneNumber(userDTO.getPhoneNumber());
		}
		if (userDTO.getEmail() != null) {
			existingUser.setEmail(userDTO.getEmail());
		}
		if (userDTO.getAddress() != null) {
			existingUser.setAddress(userDTO.getAddress());
		}

		User updatedUser = userRepository.save(existingUser);
		UserDTO updatedUserDTO = convertToDto(updatedUser);
		return updatedUserDTO;
	}

	@Transactional
	public RestaurantOrderStatusDTO updateOrderStatus(RestaurantOrderStatus orderStatusDTO) {
		// 1. Extract data from orderStatusDTO
		Order orderDTOOrder = orderRepository.findByOrderId(orderStatusDTO.getOrder().getOrderId());
		Restaurant restaurant = restaurantRepository
				.findByRestaurantId(orderStatusDTO.getRestaurant().getRestaurantId());
		String status = orderStatusDTO.getStatus();
		// 2. Validate data
		if (restaurant == null || status == null || orderDTOOrder == null) {
			throw new IllegalArgumentException("Missing required order details.");
		}

		RestaurantOrderStatus restaurantOrderStatus = new RestaurantOrderStatus();
		restaurantOrderStatus.setRestaurant(restaurant);
		restaurantOrderStatus.setOrder(orderDTOOrder);
		restaurantOrderStatus.setStatus(status);
		restaurantOrderStatus.setOrderedAt(LocalDateTime.now());

		return  mapResOrderToDTO(restaurantOrderStatusRepository.save(restaurantOrderStatus));
	}

	
	
	@Transactional
	public void removeOrderItem(Integer orderId, Integer userId) {
		Order order = orderRepository.findByOrderId(orderId);
		if (!order.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("Unauthorized to remove this order.");
		}
		orderRepository.delete(order);
	}

	private ResFoodItemDTO mapFoodItemToResDTO(FoodItem foodItem) {
		ResFoodItemDTO dto = new ResFoodItemDTO(foodItem.getFoodId(), foodItem.getFoodName(), foodItem.getFoodType(),
				foodItem.getDescription(), foodItem.getPrice(), foodItem.getImageURL(), foodItem.getRating());
		return dto;
	}

	private RestaurantDTO mapRestaurantToDTO(Restaurant restaurant) {
		return new RestaurantDTO(restaurant.getRestaurantId(), restaurant.getRestaurantName(), restaurant.getAddress(),
				restaurant.getCuisine(), restaurant.getOwnerName());
	}

	private FoodItemDTO mapFoodItemToDTO(FoodItem foodItem) {
		return new FoodItemDTO(foodItem.getFoodId(), foodItem.getFoodName(), foodItem.getFoodType(),
				foodItem.getDescription(), foodItem.getPrice(), foodItem.getImageURL(), foodItem.getRating(),
				mapRestaurantToDTO(foodItem.getRestaurant()));
	}

	private OrderDTO mapOrderToDTO(Order order) {
		return new OrderDTO(order.getOrderId(), mapUserToDTO(order.getUser()), mapFoodItemToDTO(order.getFoodItem()), order.getTotalPrice(),
				order.getQuantity(), order.getDate());
	}
	

	
	private CusOrderDTO mapOrderToCusDTO(Order order) {
		return new CusOrderDTO(order.getOrderId(), mapFoodItemToResDTO(order.getFoodItem()), order.getTotalPrice(),
				order.getQuantity(), order.getDate());
	}
	private RestaurantOrderStatusDTO mapResOrderToDTO(RestaurantOrderStatus entity) {
		return new RestaurantOrderStatusDTO(
				entity.getRestaurantOrderStatusId(),
				mapRestaurantToDTO(entity.getRestaurant()),
				mapOrderToDTO(entity.getOrder()),
				entity.getStatus(),
				entity.getOrderedAt()
				);
	}
	

	private UserDTO mapUserToDTO(User user) {
		return new UserDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhoneNumber(),
				user.getAddress(), user.getRole());
	}

	private UserDTO convertToDto(User user) {
		return new UserDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getPhoneNumber(),
				user.getAddress(), user.getRole());
	}
}
