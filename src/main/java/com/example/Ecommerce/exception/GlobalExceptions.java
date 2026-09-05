package com.example.Ecommerce.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Ecommerce.ClassDto.ResourceNotFoundError;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundError> resourceNotFound(ResourceNotFoundException e) {
        ResourceNotFoundError error = new ResourceNotFoundError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResourceNotFoundError> illegalArgument(IllegalArgumentException e) {
        ResourceNotFoundError error = new ResourceNotFoundError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResourceNotFoundError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().isEmpty() 
                ? "Validation error" 
                : ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ResourceNotFoundError error = new ResourceNotFoundError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResourceNotFoundError> handleGenericException(Exception e) {
        ResourceNotFoundError error = new ResourceNotFoundError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}