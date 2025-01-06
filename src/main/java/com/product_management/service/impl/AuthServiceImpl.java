package com.product_management.service.impl;

import com.product_management.exceptions.UserAlreadyRegisteredException;
import com.product_management.exceptions.UserNotFoundException;
import com.product_management.mapper.UserMapper;
import com.product_management.model.dto.LoginRequest;
import com.product_management.model.dto.LoginResponse;
import com.product_management.model.dto.UserSignupDto;
import com.product_management.repositories.UserRepo;
import com.product_management.security.utils.JwtUtil;
import com.product_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.product_management.enums.ErrorCodes.USER_ALREADY_REGISTERED;
import static com.product_management.enums.ErrorCodes.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepo userRepo;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public String signup(final UserSignupDto signupDto) {
        userRepo.findByUsername(signupDto.getUsername()).ifPresent(user -> {
            throw new UserAlreadyRegisteredException(USER_ALREADY_REGISTERED, "User is already registered with username : " + signupDto.getUsername());
        });

        var user = mapper.fromSignupRequest(signupDto);
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        userRepo.save(user);
        return "User Successfully registered";
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var user = userRepo.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND, "User does not exist with username"));
        return LoginResponse.builder()
                .id(user.getId())
                .accessToken(jwtUtil.generateAccessToken(user.getUsername()))
                .refreshToken(jwtUtil.generateRefreshToken(user.getUsername()))
                .build();
    }
}
