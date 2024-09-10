package com.itacademy.virtualpet.service;

import com.itacademy.virtualpet.model.Pet;
import com.itacademy.virtualpet.model.User;
import com.itacademy.virtualpet.exception.UserNotFoundException;
import com.itacademy.virtualpet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> addPetToUser(String userId, String authenticatedUsername, Pet newPet) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with ID '" + userId + "' not found.")))
                .flatMap(user -> {
                    if (!user.getUsername().equals(authenticatedUsername)) {
                        return Mono.error(new AccessDeniedException("You are not authorized to modify this user"));
                    }
                    if (user.getPets() == null) {
                        user.setPets(new ArrayList<>());
                    }
                    user.getPets().add(newPet);
                    return userRepository.save(user);
                });
    }

    public Mono<List<Pet>> getUserPets(String userId, String authenticatedUsername) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with ID '" + userId + "' not found.")))
                .flatMap(user -> {
                    if (!user.getUsername().equals(authenticatedUsername)) {
                        return Mono.error(new AccessDeniedException("You are not authorized to view this user's pets"));
                    }
                    if (user.getPets() == null) {
                        return Mono.just(Collections.emptyList());
                    }
                    return Mono.just(user.getPets());
                });
    }
}
