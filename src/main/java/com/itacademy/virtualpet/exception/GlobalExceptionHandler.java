package com.itacademy.virtualpet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public Mono<ResponseEntity<String>> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException error) {
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(error.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ResponseEntity<String>> handleUserNotFoundException(UserNotFoundException error) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handleGenericException(String message) {
        if (message.isEmpty()) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred."));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message));
    }
}
