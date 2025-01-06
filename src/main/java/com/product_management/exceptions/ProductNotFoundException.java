package com.product_management.exceptions;

import com.product_management.enums.ErrorCodes;
import lombok.Getter;
import lombok.Setter;

/**
 * Custom exception for cases where a product is not found.
 * This exception is thrown when a requested product cannot be found in the system.
 * It extends {@link RuntimeException} to provide a custom error response.
 *
 * @author Bhupendra Birla
 */
@Getter
@Setter
public class ProductNotFoundException extends RuntimeException {

    private final String message;

    private final ErrorCodes errorCode;


    public ProductNotFoundException(final ErrorCodes errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

}