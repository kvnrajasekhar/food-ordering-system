package com.feast.server_main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.webjars.NotFoundException;

import com.feast.server_main.dto.UserDTO;
import com.feast.server_main.model.User;
import com.feast.server_main.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO login(UserDTO userDTO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        String email = userDTO.getEmail();
        System.out.println("Received email for login: " + email); // Add this
        User user = userRepository.getByEmail(email);

        if (user == null) {
            throw new NotFoundException("User not found");
        }
        System.out.println("Encoded password from DB: " + user.getPassword());  //add this
        System.out.println("Raw password from request: " + userDTO.getPassword()); //add this
        if (bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return convertToDto(user);
        } else {
            throw new NotFoundException("Invalid password");
        }
    }

    public UserDTO signup(UserDTO userDTO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        String email = userDTO.getEmail();
        User existingUser = userRepository.getByEmail(email);

        if (existingUser != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        User newUser = new User();
        newUser.setUserName(userDTO.getUserName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setAddress(userDTO.getAddress());
        newUser.setRole(userDTO.getRole());
        newUser.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(newUser);
        return convertToDto(savedUser);
    }

    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole(),
        		null);
    }
}