package com.itacademy.virtualpet.service;

import com.itacademy.virtualpet.exception.InvalidCredentialsException;
import com.itacademy.virtualpet.exception.UserNotFoundException;
import com.itacademy.virtualpet.exception.UsernameAlreadyTakenException;
import com.itacademy.virtualpet.model.LoginResponse;
import com.itacademy.virtualpet.model.User;
import com.itacademy.virtualpet.model.Pet;
import com.itacademy.virtualpet.repository.PetRepository;
import com.itacademy.virtualpet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, PetRepository petRepository, BCryptPasswordEncoder passwordEncoder) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<Object> register(String username, String password) {
        return userRepository.findByUsername(username)
                .flatMap(existingUser -> Mono.error(new UsernameAlreadyTakenException("Username '" + username + "' is already taken.")))
                .switchIfEmpty(
                        // Automatically set isAdmin to true if the username is 'admin', otherwise set to false
                        userRepository.save(new User(username, passwordEncoder.encode(password), new ArrayList<>(), username.equalsIgnoreCase("admin")))
                );
    }

    public Mono<LoginResponse> login(String username, String password) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with username '" + username + "' not found.")))
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return petRepository.findAllById(user.getPetIds())
                                .collectList()
                                .flatMap(pets -> {
                                    user.setPetIds(pets.stream().map(Pet::getId).collect(Collectors.toList()));

                                    String token = jwtUtil.createToken(user);

                                    return Mono.just(new LoginResponse(user, token));
                                });
                    } else {
                        return Mono.error(new InvalidCredentialsException("Invalid username or password."));
                    }
                });
    }
}
