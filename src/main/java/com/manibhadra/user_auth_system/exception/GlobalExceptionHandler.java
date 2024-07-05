package com.manibhadra.user_auth_system.exception;

import com.manibhadra.user_auth_system.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomExceptions.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(CustomExceptions ex) {
        ApiResponse<Object> response = new ApiResponse<>(null, false, HttpStatus.BAD_REQUEST.value(), "Failed", ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
