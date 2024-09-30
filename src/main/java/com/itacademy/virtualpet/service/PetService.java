package com.itacademy.virtualpet.service;

import com.itacademy.virtualpet.exception.PetNotFoundException;
import com.itacademy.virtualpet.model.Pet;
import com.itacademy.virtualpet.repository.PetRepository;
import com.itacademy.virtualpet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    public Mono<Pet> updatePetState(String petId, Pet updatedPet, String authenticatedUsername) {
        return petRepository.findById(petId)
                .switchIfEmpty(Mono.error(new PetNotFoundException("Pet with ID '" + petId + "' not found.")))
                .flatMap(pet -> userRepository.findById(pet.getUserId())
                        .flatMap(user -> {
                            if (!user.getUsername().equals(authenticatedUsername)) {
                                return Mono.error(new AccessDeniedException("You are not authorized to modify this pet"));
                            }
                            pet.setEnergyLevel(updatedPet.getEnergyLevel());
                            pet.setMood(updatedPet.getMood());
                            pet.setNeeds(updatedPet.getNeeds());
                            return petRepository.save(pet);
                        }));
    }

    public Mono<Pet> getPetById(String petId, String authenticatedUsername) {
        return petRepository.findById(petId)
                .switchIfEmpty(Mono.error(new PetNotFoundException("Pet with ID '" + petId + "' not found.")))
                .flatMap(pet -> userRepository.findById(pet.getUserId())
                        .flatMap(user -> {
                            if (!user.getUsername().equals(authenticatedUsername)) {
                                return Mono.error(new AccessDeniedException("You are not authorized to view this pet"));
                            }
                            return Mono.just(pet);
                        }));
    }
}
