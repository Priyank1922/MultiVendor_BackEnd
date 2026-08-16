package com.example.Ecommerce.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}