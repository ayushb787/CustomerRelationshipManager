package org.demo.crm.exceptions;

import org.demo.crm.auth.exception.UserAlreadyExistsException;
import org.demo.crm.auth.exception.UserNotFoundException;
import org.demo.crm.auth.model.ApiResponse;
import org.demo.crm.customer.exception.CustomerNotFoundException;
import org.demo.crm.customer.exception.DuplicateEmailException;
import org.demo.crm.customer.exception.DuplicatePhoneException;
import org.demo.crm.lead.exception.LeadNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle UserAlreadyExistsException
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle CustomerNotFoundException
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle DuplicateEmailException
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicateEmailException(DuplicateEmailException ex) {
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle DuplicatePhoneException
    @ExceptionHandler(DuplicatePhoneException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicatePhoneException(DuplicatePhoneException ex) {
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LeadNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleLeadNotFoundException(LeadNotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle other exceptions (e.g., generic errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        ApiResponse<String> response = new ApiResponse<>(false, "An unexpected error occurred: " + ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        ApiResponse<String> response = ApiResponse.error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiResponse<String>> handleSecurityException(SecurityException ex) {
        ApiResponse<String> response = ApiResponse.error(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Handle validation exceptions (e.g., invalid fields in request body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        ApiResponse<Map<String, String>> response = new ApiResponse<>(false, "Validation error", errors, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
