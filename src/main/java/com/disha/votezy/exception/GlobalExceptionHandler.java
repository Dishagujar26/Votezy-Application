package com.disha.votezy.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
      @ExceptionHandler(value = ResourceNotFoundException.class)
      public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);  //404
    	  
      }
      
      @ExceptionHandler(value = DuplicateResourceException.class)
      public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException ex){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(),ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT); //409
    	  
      }
      
      @ExceptionHandler(value = VoteNotAllowedException.class)
      public ResponseEntity<ErrorResponse> handleVoteNotAllowed(VoteNotAllowedException ex){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(),ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN); //403
    	  
      }
      //DTO (request time) → MethodArgumentNotValidException
      //MethodArgumentNotValidException is thrown when the arguments passed to a controller method fail validation — usually when using @Valid or @Validated.
      //Thrown when validation fails on request DTO (annotated with @Valid or @Validated) in controller method parameters.
      
      @ExceptionHandler(value = MethodArgumentNotValidException.class)
      public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex){
    		    Map<String, String> errors = new HashMap<>();
    		    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

    		    for (FieldError error : fieldErrors) {
    		        errors.put(error.getField(), error.getDefaultMessage());
    		    }
    		    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); //400	  
      }
      
      //Entity (persistence time) → ConstraintViolationException
      //Thrown when JPA/Hibe-rnate tries to persist an entity that violates validation constraints like @NotNull, @Email, etc. on the Entity class.
    
      //Handles and returns detailed field-level error messages when input validation constraints (like @NotNull, @Size, etc.) are violated, responding with a 400 Bad Request.
      @ExceptionHandler(ConstraintViolationException.class)
      public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
          Map<String, String> errors = new HashMap<>();
          for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
              String field = violation.getPropertyPath().toString();
              errors.put(field, violation.getMessage());
          }
          return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
      }

      //It handles any unhandled exceptions globally 
      @ExceptionHandler(value = Exception.class)
      public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex ){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR); //500
    	  //401 Unauthorized //201 created
      }
      
}
