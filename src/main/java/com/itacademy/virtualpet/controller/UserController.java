package com.itacademy.virtualpet.controller;

import com.itacademy.virtualpet.model.Pet;
import com.itacademy.virtualpet.service.PetService;
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

    @Autowired
    private PetService petService;

    @PostMapping("/{userId}/addPet")
    public Mono<ResponseEntity<List<Pet>>> addPetToUser(
            @PathVariable String userId,
            @RequestBody Pet pet,
            Authentication authentication) {

        if (authentication == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        String authenticatedUsername = authentication.getName();

        return userService.addPetToUser(userId, pet, authenticatedUsername)
                .flatMap(updatedPets -> Mono.just(ResponseEntity.ok(updatedPets)))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    System.out.println("Error: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }

    @GetMapping("/{userId}/pets")
    public Mono<ResponseEntity<List<Pet>>> getUserPets(
            @PathVariable String userId,
            Authentication authentication) {

        if (authentication == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        String authenticatedUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        System.out.println("Is admin: " + isAdmin);

        if (isAdmin) {
            // If the user is an admin, return all pets
            return userService.getAllPets()
                    .doOnNext(pets -> System.out.println("Fetched pets: " + pets))
                    .map(pets -> ResponseEntity.ok(pets))
                    .onErrorResume(e -> {
                        System.out.println("Error fetching all pets: " + e.getMessage());
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                    });
        } else {
            // If the user is not an admin, return only their own pets
            return userService.getUserPets(userId, authenticatedUsername)
                    .map(pets -> ResponseEntity.ok(pets))
                    .defaultIfEmpty(ResponseEntity.notFound().build())
                    .onErrorResume(e -> {
                        System.out.println("Error fetching user pets: " + e.getMessage());
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                    });
        }
    }
}
