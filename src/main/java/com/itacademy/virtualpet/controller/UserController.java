package com.itacademy.virtualpet.controller;

import com.itacademy.virtualpet.model.Pet;
import com.itacademy.virtualpet.model.User;
import com.itacademy.virtualpet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}/addPet")
    public Mono<ResponseEntity<User>> addPetToUser(@PathVariable String userId, @RequestBody Pet pet, Authentication authentication) {
        if (authentication == null) {
            System.out.println("Authentication is null, returning UNAUTHORIZED.");
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        String authenticatedUsername = authentication.getName();
        return userService.findUserById(userId)
                .flatMap(user -> {
                    return userService.addPetToUser(userId, authenticatedUsername, pet)
                            .map(updatedUser -> {
                                System.out.println("User updated successfully: " + updatedUser.getUsername());
                                return ResponseEntity.ok(updatedUser);
                            });
                });
    }

    @GetMapping("/{userId}/pets")
    public Mono<ResponseEntity<List<Pet>>> getUserPets(@PathVariable String userId, Authentication authentication) {
        if (authentication == null) {
            System.out.println("Authentication is null, returning UNAUTHORIZED.");
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        String authenticatedUsername = authentication.getName();
        return userService.findUserById(userId)
                .flatMap(user -> {
                    return userService.getUserPets(userId, authenticatedUsername)
                            .map(pets -> {
                                System.out.println("Pets returned: " + pets);
                                return ResponseEntity.ok(pets);
                            })
                            .defaultIfEmpty(ResponseEntity.notFound().build())
                            .onErrorResume(e -> {
                                System.out.println("Error: " + e.getMessage());
                                return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
                            });
                });
    }
}
