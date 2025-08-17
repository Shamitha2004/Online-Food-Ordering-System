package com.project.controllers;

import com.project.entities.User;
import com.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // API: Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully with username: " + savedUser.getUsername());
    }

    // API: Login user
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (loggedInUser != null) {
            return ResponseEntity.ok("Login successful! Welcome " + loggedInUser.getUsername());
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
