package com.feast.server_main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feast.server_main.model.User;
import com.feast.server_main.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User register(User user) {
		return userRepository.save(user);
	}
	
	public Optional<User> login(String email,String password){
		return userRepository.findByEmail(email).filter(u->
		u.getPassword().equals(password));
	}
	
	public User updateProfile(User user) {
		return userRepository.save(user);
	}
	public Optional<User> getUserById(Long id){
		return userRepository.findById(id);
	}
	
	
}
