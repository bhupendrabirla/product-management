package com.product_management.exceptions.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_management.enums.ErrorCodes;
import com.product_management.model.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom handler for handling authentication errors.
 * This class is used to intercept authentication failures and return a customized error response to the client.
 * It implements the {@link AuthenticationEntryPoint} interface to handle unauthorized access attempts.
 * The response contains an error message and the specific error code (UNAUTHORIZED).
 *
 * @author Bhupendra Birla
 */
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException {
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.builder()
                .message(authException.getMessage()).errorCode(ErrorCodes.UNAUTHORIZED).build()));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
