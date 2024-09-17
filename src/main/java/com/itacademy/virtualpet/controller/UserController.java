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
        System.out.println("Authentication Name: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());

        String authenticatedUsername = authentication.getName();

        return userService.addPetToUser(userId, authenticatedUsername, pet)
                .map(updatedUser -> ResponseEntity.ok(updatedUser))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{userId}/pets")
    public Mono<ResponseEntity<List<Pet>>> getUserPets(@PathVariable String userId, Authentication authentication) {
        String authenticatedUsername = authentication.getName();
        return userService.getUserPets(userId, authenticatedUsername)
                .map(pets -> ResponseEntity.ok(pets))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorReturn(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
