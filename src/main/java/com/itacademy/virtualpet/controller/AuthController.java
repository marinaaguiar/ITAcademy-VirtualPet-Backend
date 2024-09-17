package com.itacademy.virtualpet.controller;

import com.itacademy.virtualpet.model.User;
import com.itacademy.virtualpet.service.AuthService;
import com.itacademy.virtualpet.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Mono<ResponseEntity<Map<String, Object>>> register(@RequestBody User user) {
        return authService.register(user.getUsername(), user.getPassword())
                .map(savedUser -> {
                    String token = jwtUtil.generateToken((User) savedUser);
                    Map<String, Object> response = new HashMap<>();
                    response.put("user", savedUser);
                    response.put("token", token);
                    return ResponseEntity.ok(response);
                });
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, Object>>> login(@RequestBody User user) {
        return authService.login(user.getUsername(), user.getPassword())
                .map(loggedInUser -> {
                    String token = jwtUtil.generateToken((User) loggedInUser);
                    Map<String, Object> response = new HashMap<>();
                    response.put("user", loggedInUser);
                    response.put("token", token);
                    return ResponseEntity.ok(response);
                });
    }
}
