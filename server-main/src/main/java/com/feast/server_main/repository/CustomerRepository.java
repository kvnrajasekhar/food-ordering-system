package com.feast.server_main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.feast.server_main.model.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

