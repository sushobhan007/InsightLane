package com.blog.insight_lane.exceptions;

import com.blog.insight_lane.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        String message = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse> resourceAlreadyExistsExceptionHandler(DuplicateResourceException exception) {
        String message = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgsNotValidExceptionHandler(
            MethodArgumentNotValidException exception) {
        Map<String, Object> responseMap = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            responseMap.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPropertiesFormatException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPropertiesFormatException(
            InvalidPropertiesFormatException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("provided", exception.getMessage());
        response.put("message", "Provided file format is invalid");
        response.put("valid format", "jpg, jpeg, png");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
