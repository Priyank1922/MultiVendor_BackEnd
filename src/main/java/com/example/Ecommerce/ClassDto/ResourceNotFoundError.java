package com.example.Ecommerce.ClassDto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record ResourceNotFoundError (

	 LocalDateTime time,

	 int status,

	 String message) {

}