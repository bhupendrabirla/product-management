package com.product_management.exceptions;

import com.product_management.enums.ErrorCodes;
import lombok.Getter;
import lombok.Setter;

/**
 * Custom exception for cases where a user is not found.
 * This exception is thrown when an operation attempts to retrieve a user who does not exist in the system.
 * It extends {@link RuntimeException} to provide a custom error response.
 *
 * @author Bhupendra Birla
 */
@Getter
@Setter
public class UserNotFoundException extends RuntimeException {

    private final String message;

    private final ErrorCodes errorCode;

    public UserNotFoundException(final ErrorCodes errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

}
