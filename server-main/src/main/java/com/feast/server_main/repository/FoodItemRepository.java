// com.feast.server.repository.CartItemRepository.java
package com.feast.server_main.repository;

import com.feast.server_main.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {

}