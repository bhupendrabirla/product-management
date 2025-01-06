package com.product_management.security.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("app.security.config")
public class SecurityConfigProperties {

    private String secretKey;
    private Long accessTokenExpiry;
    private Long refreshTokenExpiry;
    private List<String> publicUrls;

}
