package com.itacademy.virtualpet.controller;

import com.itacademy.virtualpet.model.User;
import com.itacademy.virtualpet.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<User>> register(@RequestBody User user) {
        return authService.register(user.getUsername(), user.getPassword())
                .map(savedUser -> ResponseEntity.ok((User) savedUser));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<User>> login(@RequestBody User user) {
        return authService.login(user.getUsername(), user.getPassword())
                .map(loggedInUser -> ResponseEntity.ok(loggedInUser));
    }
}
