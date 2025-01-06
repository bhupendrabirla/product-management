package com.product_management.service.impl;

import com.product_management.exceptions.ProductNotFoundException;
import com.product_management.mapper.ProductMapper;
import com.product_management.model.dto.ProductDto;
import com.product_management.model.entities.Product;
import com.product_management.repositories.ProductRepo;
import com.product_management.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.product_management.enums.ErrorCodes.PRODUCT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final ProductMapper productMapper;


    /**
     * Adds a new product.
     *
     * @param productDto the product data to be saved
     * @return the created product as a ProductDto
     */
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        return productMapper.toDto(productRepo.save(productMapper.toEntity(productDto)));
    }

    /**
     * Get product by its ID.
     *
     * @param id the ID of the product to be retrieved
     * @return the product as a ProductDto
     * @throws ProductNotFoundException if no product with the given ID is found
     */
    @Override
    public ProductDto getProductById(Long id) {
        log.info("Fetching product by id : {}", id);
        return productMapper.toDto(getProduct(id));
    }

    /**
     * Get all products from the repository and maps them to DTOs.
     *
     * @param pageable - for pagination
     * @return a list of all products as ProductDto objects
     */
    @Override
    public Page<ProductDto> getProducts(Pageable pageable) {
        return productRepo.findAll(pageable).map(productMapper::toDto);
    }

    /**
     * Updates an existing products name, description and price.
     *
     * @param id         the ID of the product to be updated
     * @param productDto the new product data to update the product with
     * @return the updated product as a ProductDto
     * @throws ProductNotFoundException if no product with the given ID is found
     */
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        log.info("updating product for id : {}", id);
        Product product = getProduct(id);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return productMapper.toDto(productRepo.save(product));
    }


    /**
     * Deletes the product by id.
     *
     * @param id the ID of the product to be deleted
     * @throws ProductNotFoundException if no product with the given ID is found
     */

    @Override
    public String deleteProduct(Long id) {
        productRepo.delete(getProduct(id));
        return "Product removed successfully";
    }

    private Product getProduct(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, "Product not found"));
    }
}
