package com.product_management.exceptions.handlers;

import com.product_management.exceptions.ProductNotFoundException;
import com.product_management.exceptions.UserAlreadyRegisteredException;
import com.product_management.exceptions.UserNotFoundException;
import com.product_management.model.dto.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static com.product_management.enums.ErrorCodes.*;
import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class ExceptionTranslator {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        var allErrors = ex.getBindingResult().getAllErrors();
        var errorMessage = allErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining(" | "));

        return ResponseEntity.badRequest().body(
                ErrorResponse.builder().message(errorMessage).errorCode(VALIDATION_FAILED).build()
        );
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyRegisteredException(final UserAlreadyRegisteredException ex) {
        return ResponseEntity.status(NOT_FOUND)
                .body(ErrorResponse.builder().errorCode(ex.getErrorCode()).message(ex.getMessage()).build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(final UserNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND)
                .body(ErrorResponse.builder().errorCode(ex.getErrorCode()).message(ex.getMessage()).build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(final BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder()
                .message("Username or password do not match!")
                .errorCode(BAD_CREDENTIALS).build());
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(final ProductNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND)
                .body(ErrorResponse.builder().errorCode(ex.getErrorCode()).message(ex.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(final Exception ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder().message(ex.getMessage()).errorCode(INTERNAL_ERROR).build());
    }

}
