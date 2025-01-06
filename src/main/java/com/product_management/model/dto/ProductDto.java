package com.product_management.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

    private Long id;

    @NotBlank(message = "Invalid Product name")
    private String name;

    private String description;

    @NotNull(message = "Price can not be null")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;
}
