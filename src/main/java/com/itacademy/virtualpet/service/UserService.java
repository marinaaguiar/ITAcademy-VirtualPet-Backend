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

    public Mono<User> findUserById(String userId) {
        return userRepository.findById(userId);
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

                    System.out.println("Adding pet: " + newPet.getName());
                    user.getPets().add(newPet);
                    Mono<User> saveNewUser = userRepository.save(user);
                    return saveNewUser;
                });
    }

    public Mono<List<Pet>> getUserPets(String userId, String authenticatedUsername) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with ID '" + userId + "' not found.")))
                .flatMap(user -> {
                    if (!user.getUsername().equals(authenticatedUsername)) {
                        System.out.println("Access denied for user: " + authenticatedUsername);
                        return Mono.error(new AccessDeniedException("You are not authorized to view this user's pets"));
                    }

                    if (user.getPets() == null || user.getPets().isEmpty()) {
                        System.out.println("User has no pets, returning empty list.");
                        return Mono.just(Collections.emptyList());
                    }
                    System.out.println("User's pets: " + user.getPets().size());
                    return Mono.just(user.getPets());
                });
    }
}
