package com.product_management.control;

import com.product_management.model.dto.LoginRequest;
import com.product_management.model.dto.LoginResponse;
import com.product_management.model.dto.UserSignupDto;
import com.product_management.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * AuthResource is a REST controller that provides authentication endpoints for user signup and login.
 * It exposes APIs for registering new users and for logging in users to generate JWT tokens.
 *
 * @author Bhupendra Birla
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Resource", description = "For Authentication")
public class AuthResource {


    private final AuthService authService;

    /**
     * Endpoint for user signup. It accepts the user registration data (UserSignupDto) and processes it.
     * Returns a success message upon successful registration.
     *
     * @param signupDto the user signup data
     * @return ResponseEntity containing a success message with HTTP status CREATED
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupDto signupDto) {
        return new ResponseEntity<>(authService.signup(signupDto), CREATED);
    }

    /**
     * Endpoint for user login. It accepts user credentials (LoginRequest) and generates a JWT token for authentication.
     *
     * @param loginRequest the user login credentials
     * @return ResponseEntity containing the generated JWT token wrapped in LoginResponse with HTTP status OK
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
