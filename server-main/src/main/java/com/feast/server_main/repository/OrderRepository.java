package com.feast.server_main.repository;

import com.feast.server_main.model.Order;
import com.feast.server_main.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Order findByOrderId(Integer orderId);
	List<Order> findByUser(User user);
	
	@Query("SELECT o FROM Order o JOIN FETCH o.foodItem fi JOIN FETCH fi.restaurant WHERE o.user = :user")
    List<Order> findByUserWithFoodItemAndRestaurant(User user);
}