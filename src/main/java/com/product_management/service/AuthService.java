package com.product_management.service;

import com.product_management.model.dto.LoginRequest;
import com.product_management.model.dto.LoginResponse;
import com.product_management.model.dto.UserSignupDto;

public interface AuthService {

    String signup(UserSignupDto signupDto);

    LoginResponse login(LoginRequest loginRequest);

}
