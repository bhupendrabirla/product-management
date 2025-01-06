package com.product_management.control;

import com.product_management.model.dto.ProductDto;
import com.product_management.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * REST controller for handling product-related API operations.
 * Provides endpoints to add, retrieve, update, and delete products.
 *
 * @author Bhupendra Birla
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product Resources", description = "For Product APIs")
public class ProductResource {


    private final ProductService productService;

    /**
     * Adds a new product.
     *
     * @param productDto the product data to be added
     * @return a ResponseEntity containing the added {@link ProductDto} and an HTTP status of CREATED
     */
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.CREATED);
    }

    /**
     * Get product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return a ResponseEntity containing the {@link ProductDto} and an HTTP status of OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }


    /**
     * Retrieves a paginated list of products.
     *
     * @param pageable pagination information, optional
     * @return a ResponseEntity containing a paginated list of {@link ProductDto} and an HTTP status of OK
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(@RequestParam(required = false) Pageable pageable) {
        return new ResponseEntity<>(productService.getProducts(Optional.ofNullable(pageable).orElse(Pageable.unpaged())).getContent(), HttpStatus.OK);
    }


    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @return a ResponseEntity with a confirmation message and an HTTP status of OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }

    /**
     * Updates an existing product.
     *
     * @param id         the ID of the product to update
     * @param productDto the new data for the product
     * @return a ResponseEntity containing the updated {@link ProductDto} and an HTTP status of OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProduct(id, productDto), HttpStatus.OK);
    }


}
