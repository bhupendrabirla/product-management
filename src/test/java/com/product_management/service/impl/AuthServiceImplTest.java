package com.product_management.service.impl;

import com.product_management.exceptions.UserAlreadyRegisteredException;
import com.product_management.mapper.UserMapper;
import com.product_management.model.dto.LoginRequest;
import com.product_management.model.dto.UserSignupDto;
import com.product_management.model.entities.User;
import com.product_management.repositories.UserRepo;
import com.product_management.security.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    private UserSignupDto userSignupDto;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userSignupDto = new UserSignupDto();
        userSignupDto.setUsername("admin");
        userSignupDto.setPassword("password");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password");

        user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("encodedPassword");
    }

    @Test
    void testSignup_Success() {
        // Arrange
        when(userRepo.findByUsername(userSignupDto.getUsername())).thenReturn(Optional.empty());
        when(userMapper.fromSignupRequest(userSignupDto)).thenReturn(user);
        when(passwordEncoder.encode(userSignupDto.getPassword())).thenReturn("encodedPassword");
        when(userRepo.save(user)).thenReturn(user);
        var result = authService.signup(userSignupDto);
        assertEquals("User Successfully registered", result);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testSignup_UserAlreadyExists() {
        when(userRepo.findByUsername(userSignupDto.getUsername())).thenReturn(Optional.of(user));
        var exception = assertThrows(UserAlreadyRegisteredException.class, () -> authService.signup(userSignupDto));
        assertEquals("User is already registered with username : admin", exception.getMessage());
    }

    @Test
    void testLogin_Success() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null); // Mocking successful authentication
        when(userRepo.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        when(jwtUtil.generateAccessToken(user.getUsername())).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(user.getUsername())).thenReturn("refreshToken");
        var result = authService.login(loginRequest);
        assertNotNull(result);
        assertEquals("accessToken", result.getAccessToken());
        assertEquals("refreshToken", result.getRefreshToken());
        verify(userRepo, times(1)).findByUsername(loginRequest.getUsername());
    }


}