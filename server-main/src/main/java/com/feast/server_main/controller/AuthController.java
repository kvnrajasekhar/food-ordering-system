package com.feast.server_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.webjars.NotFoundException;

@RestController
@CrossOrigin("http://127.0.0.1:5500")
public class AuthController {
	
	@Autowired
    private AuthService authService;
	
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(AuthService authService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authService = authService;
       this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        System.out.println("Received login request for email: " + userDTO.getEmail());  //  Add this
        try {
            UserDTO user = authService.login(userDTO, bCryptPasswordEncoder);
            System.out.println("Login successful for user: " + user.getEmail()); // Add this
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            System.out.println("Login failed: User not found for email: " + userDTO.getEmail()); // Add this
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDTO) {
        UserDTO user = authService.signup(userDTO, bCryptPasswordEncoder);
        return ResponseEntity.ok(user);
        
    }
}