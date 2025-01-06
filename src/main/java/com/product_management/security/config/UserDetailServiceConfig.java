package com.product_management.security.config;

import com.product_management.exceptions.UserNotFoundException;
import com.product_management.repositories.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

import static com.product_management.enums.ErrorCodes.USER_NOT_FOUND;

@Configuration
public class UserDetailServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(final UserRepo userRepo) {
        return (username -> {
            var user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND, "Incorrect username or password"));
            return new User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
        });
    }

}
