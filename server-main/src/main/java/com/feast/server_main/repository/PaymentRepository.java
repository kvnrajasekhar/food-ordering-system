// com.feast.server.repository.PaymentRepository.java
package com.feast.server_main.repository;

import com.feast.server_main.model.Order;
import com.feast.server_main.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // You can add custom query methods here if needed.
    // For example, finding payment by order:
    Optional<Payment> findByOrder(Order order);
}