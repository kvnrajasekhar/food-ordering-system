package com.feast.server_main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.webjars.NotFoundException; // Consider using Spring's DataAccessException
import org.springframework.dao.EmptyResultDataAccessException; // Import this
import org.springframework.transaction.annotation.Transactional; // Import this
import org.slf4j.Logger; // Import SLF4J
import org.slf4j.LoggerFactory;

import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.User;
import com.feast.server_main.repository.UserRepository;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class); // Initialize logger

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public User signup(User user) {
        String email = user.getEmail();
        User existingUser = userRepository.getByEmail(email);

        if (existingUser != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setAddress(user.getAddress());
        newUser.setRole(user.getRole());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(newUser);
        logger.info("User signed up successfully: {}", savedUser.getEmail()); // Log successful signup
        return savedUser;
    }

    public User login(User currUser) {
        String email = currUser.getEmail();
        logger.info("Received login request for email: {}", email); // Use logger
        User user = null;
        try {
            user = userRepository.getByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            logger.error("User not found for email: {}", email); // Log error
            throw new NotFoundException("User not found");
        }
        logger.debug("Encoded password from DB: {}", user.getPassword()); // Use logger and debug
        logger.debug("Raw password from request: {}", currUser.getPassword()); // Use logger and debug
        if (bCryptPasswordEncoder.matches(currUser.getPassword(), user.getPassword())) {
            logger.info("Login successful for user: {}", email); // Log successful login
            return user;
        } else {
            logger.warn("Invalid password for user: {}", email); // Log warning
            throw new NotFoundException("Invalid password");
        }
    }

    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole());
    }
}
