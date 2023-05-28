package com.demo.route.price.calculator.controller.advice;

import com.demo.route.price.calculator.exception.InvalidRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerConfig {
    private static final String BAD_REQUEST_MESSAGE = "Bad Request due to: ";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(Exception ex, HttpServletRequest request) {
        return handleValidationException(ex, request);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(Exception ex, HttpServletRequest request) {
        return handleValidationException(ex, request);
    }

    private ResponseEntity<ErrorResponse> handleValidationException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(BAD_REQUEST_MESSAGE + ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}