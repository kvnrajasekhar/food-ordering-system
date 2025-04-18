// com.feast.server.repository.CartItemRepository.java
package com.feast.server_main.repository;

import com.feast.server_main.model.CartItem;
import com.feast.server_main.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCustomer(Customer customer);
}