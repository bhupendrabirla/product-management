package com.product_management.exceptions;

import com.product_management.enums.ErrorCodes;
import lombok.Getter;
import lombok.Setter;

/**
 * Custom exception for cases where a user is already registered.
 * This exception is thrown when an attempt is made to register a user who already exists in the system.
 * It extends {@link RuntimeException} to provide a custom error response.
 *
 * @author Bhupendra Birla
 */
@Getter
@Setter
public class UserAlreadyRegisteredException extends RuntimeException {

    private final String message;

    private final ErrorCodes errorCode;

    public UserAlreadyRegisteredException(final ErrorCodes errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
