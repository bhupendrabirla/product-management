package com.product_management.mapper;

import com.product_management.model.dto.ProductDto;
import com.product_management.model.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Converts a {@link Product} entity to a {@link ProductDto}.
     * This method maps the fields of the entity to the corresponding fields in the DTO.
     *
     * @param product the {@link Product} entity to be converted
     * @return the corresponding {@link ProductDto}
     */
    ProductDto toDto(Product product);

    /**
     * Converts a {@link ProductDto} to a {@link Product} entity.
     * This method maps the fields of the DTO to the corresponding fields in the entity.
     *
     * @param productDto the {@link ProductDto} to be converted
     * @return the corresponding {@link Product} entity
     */
    Product toEntity(ProductDto productDto);

}
