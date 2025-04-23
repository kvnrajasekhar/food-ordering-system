package com.feast.server_main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.feast.server_main.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}

