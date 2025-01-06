package com.product_management.exceptions.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_management.enums.ErrorCodes;
import com.product_management.model.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom handler for handling access denied errors.
 * This class is invoked when a user tries to access a resource they don't have permission to access.
 * It implements the {@link AccessDeniedHandler} interface and returns a custom error response
 * with a "FORBIDDEN" HTTP status (403) and an appropriate error message.
 *
 * @author Bhupendra Birla
 */
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) throws IOException {
        var errorResponse = ErrorResponse.builder().errorCode(ErrorCodes.UNAUTHORIZED).message("UnAuthorized").build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
