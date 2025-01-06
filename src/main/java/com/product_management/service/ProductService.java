package com.product_management.service;

import com.product_management.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Service interface for operations related to {@link com.product_management.model.entities.Product}.
 * Provides methods for managing products, such as adding, retrieving, updating, and deleting products.
 *
 * @author Bhupendra Birla
 */
public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    ProductDto getProductById(Long id);

    Page<ProductDto> getProducts(Pageable pageable);

    ProductDto updateProduct(Long id, ProductDto product);

    String deleteProduct(Long id);


}
