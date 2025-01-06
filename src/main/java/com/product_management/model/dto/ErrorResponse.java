package com.product_management.model.dto;

import com.product_management.enums.ErrorCodes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ErrorResponse implements Serializable {

    private final ErrorCodes errorCode;

    private final String message;

}
