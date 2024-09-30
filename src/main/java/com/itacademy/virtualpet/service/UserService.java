package com.itacademy.virtualpet.service;

import com.itacademy.virtualpet.model.Pet;
import com.itacademy.virtualpet.model.User;
import com.itacademy.virtualpet.exception.UserNotFoundException;
import com.itacademy.virtualpet.repository.PetRepository;
import com.itacademy.virtualpet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    public Mono<User> addPetToUser(String userId, Pet newPet, String authenticatedUsername) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with ID '" + userId + "' not found.")))
                .flatMap(user -> {
                    if (!user.getUsername().equals(authenticatedUsername)) {
                        return Mono.error(new AccessDeniedException("You are not authorized to modify this user"));
                    }

                    newPet.setUserId(userId);
                    return petRepository.save(newPet)
                            .flatMap(savedPet -> {
                                if (user.getPetIds() == null) {
                                    user.setPetIds(new ArrayList<>());
                                }
                                user.getPetIds().add(savedPet.getId());
                                return userRepository.save(user);
                            });
                });
    }

    public Mono<List<Pet>> getUserPets(String userId, String authenticatedUsername) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with ID '" + userId + "' not found.")))
                .flatMap(user -> {
                    if (!user.getUsername().equals(authenticatedUsername)) {
                        return Mono.error(new AccessDeniedException("You are not authorized to view this user's pets"));
                    }

                    if (user.getPetIds() == null || user.getPetIds().isEmpty()) {
                        return Mono.just(Collections.emptyList());
                    }

                    return petRepository.findAllById(user.getPetIds())
                            .collectList();
                });
    }
}
