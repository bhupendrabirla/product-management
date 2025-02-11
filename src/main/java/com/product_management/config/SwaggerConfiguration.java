package com.product_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Swagger configuration class to customize and configure the Swagger documentation for the API.
 * This class sets up the title, version, description, and security for the Swagger-generated API documentation.
 * It also adds a security requirement for JWT Bearer Authentication.
 *
 * @author Bhupendra Birla
 */
@Configuration
@EnableWebMvc
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product-management API doc")
                        .version("1.0.0")
                        .description("API documentation for the product-management"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .schemaRequirement("Bearer Authentication",
                        new SecurityScheme()
                                .name(AUTHORIZATION)
                                .type(HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(HEADER));
    }

}
