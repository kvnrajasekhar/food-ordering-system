// com.feast.server.repository.PaymentRepository.java
package com.feast.server_main.repository;

import com.feast.server_main.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
}