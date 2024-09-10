package com.itacademy.virtualpet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;
import com.itacademy.virtualpet.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException error) {
        ErrorResponse errorResponse = new ErrorResponse(error.getMessage(), HttpStatus.CONFLICT.value());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUserNotFoundException(UserNotFoundException error) {
        ErrorResponse errorResponse = new ErrorResponse(error.getMessage(), HttpStatus.NOT_FOUND.value());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidCredentialsException(InvalidCredentialsException error) {
        ErrorResponse errorResponse = new ErrorResponse(error.getMessage(), HttpStatus.NOT_FOUND.value());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(Exception error) {
        String message = error.getMessage().isEmpty() ? "An unexpected error occurred." : error.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
    }

}


