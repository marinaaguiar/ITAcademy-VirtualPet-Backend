package com.itacademy.virtualpet.controller;

import com.itacademy.virtualpet.model.Pet;
import com.itacademy.virtualpet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PutMapping("/{petId}")
    public Mono<ResponseEntity<Pet>> updatePetState(
            @PathVariable String petId,
            @RequestBody Pet updatedPet,
            Authentication authentication) {

        if (authentication == null) {
            System.out.println("Authentication is null, returning UNAUTHORIZED.");
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        String authenticatedUsername = authentication.getName();
        return petService.updatePetState(petId, updatedPet, authenticatedUsername)
                .map(updatedPetState -> ResponseEntity.ok(updatedPetState))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    System.out.println("Error: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }

    @GetMapping("/{petId}")
    public Mono<ResponseEntity<Pet>> getPetById(
            @PathVariable String petId,
            Authentication authentication) {

        if (authentication == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        String authenticatedUsername = authentication.getName();
        return petService.getPetById(petId, authenticatedUsername)
                .map(pet -> ResponseEntity.ok(pet))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
