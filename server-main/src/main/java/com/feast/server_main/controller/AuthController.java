package com.feast.server_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.User;
import com.feast.server_main.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.webjars.NotFoundException;

@RestController
@CrossOrigin("http://127.0.0.1:5500")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired 
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(AuthService authService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authService = authService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody User user) {
        logger.info("Received login request for email: {}", user.getEmail());
        try {
            UserDTO loggedInUser = authService.login(user);
            logger.info("Login successful for user: {}", user.getEmail());
            return ResponseEntity.ok(loggedInUser);
        } catch (NotFoundException e) {
            logger.warn("Login failed: User not found for email: {}", user.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Or .build()
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        try {
            User signedUpUser = authService.signup(user);
            logger.info("Signup successful for user: {}", signedUpUser.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(signedUpUser); // Use 201 Created
        } catch (IllegalArgumentException e) {
            logger.error("Signup failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
