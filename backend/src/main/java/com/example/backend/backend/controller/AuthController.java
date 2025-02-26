package com.example.backend.backend.controller;

import com.example.backend.backend.dto.LoginRequest;
import com.example.backend.backend.dto.LoginResponse;
import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.services.JwtService;
import com.example.backend.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UsersModel user = userService.registerUser(userDTO);
        String jwtToken = jwtService.generateToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setMessage("✅ User registered and logged in successfully");

        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest input) {
        UsersModel authenticatedUser = userService.loginUser(input);
        String jwtToken = jwtService.generateToken(authenticatedUser);


        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setMessage("Login successful");
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid token format"));
        }

        token = token.substring(7);
        userService.logout(token);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

}
