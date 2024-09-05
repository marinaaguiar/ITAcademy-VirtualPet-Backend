package com.itacademy.virtualpet.service;

import com.itacademy.virtualpet.exception.UserNotFoundException;
import com.itacademy.virtualpet.exception.UsernameAlreadyTakenException;
import com.itacademy.virtualpet.model.User;
import com.itacademy.virtualpet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<Object> register(String username, String password) {
        return userRepository.findByUsername(username)
                .flatMap(existingUser -> Mono.error(new UsernameAlreadyTakenException("Username '" + username + "' is already taken.")))
                .switchIfEmpty(
                        userRepository.save(new User(username, passwordEncoder.encode(password), null))
                );
    }

    public Mono<User> login(String username, String password) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with username '" + username + "' not found.")))
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return Mono.just(user);
                    } else {
                        return Mono.error(new UserNotFoundException("Invalid username or password."));
                    }
                });
    }
}
