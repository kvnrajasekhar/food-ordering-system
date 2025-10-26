package com.feast.server_main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.feast.server_main.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByUserName(String userName);
	 boolean existsByEmail(String email);
	User getByEmail(String email);
	User findByUserId(Integer userId);
}

