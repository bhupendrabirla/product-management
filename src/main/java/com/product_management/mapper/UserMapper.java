package com.product_management.mapper;

import com.product_management.model.dto.UserSignupDto;
import com.product_management.model.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a {@link UserSignupDto} to a {@link User} entity.
     * This method maps the fields of the signup DTO to the corresponding fields in the user entity.
     *
     * @param signupDto the {@link UserSignupDto} containing user signup information
     * @return the corresponding {@link User} entity
     */
    User fromSignupRequest(UserSignupDto signupDto);

}
