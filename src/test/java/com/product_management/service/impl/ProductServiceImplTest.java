package com.product_management.service.impl;

import com.product_management.exceptions.ProductNotFoundException;
import com.product_management.mapper.ProductMapper;
import com.product_management.model.dto.ProductDto;
import com.product_management.model.entities.Product;
import com.product_management.repositories.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private ProductMapper productMapper;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Product1");
        product.setDescription("Description1");
        product.setPrice(100.0);
        productDto = new ProductDto(1L, "Product1", "Description1", 100.0);
    }

    @Test
    void testAddProduct() {
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);
        when(productRepo.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);
        var result = productService.addProduct(productDto);
        assertNotNull(result);
        assertEquals("Product1", result.getName());
        verify(productRepo, times(1)).save(any(Product.class));
    }

    @Test
    void testGetProductById() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);
        var result = productService.getProductById(1L);
        assertNotNull(result);
        assertEquals("Product1", result.getName());
        verify(productRepo, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_ProductNotFound() {
        when(productRepo.findById(1L)).thenReturn(Optional.empty());
        var exception = assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void testGetProducts() {
        var productPage = new PageImpl<>(List.of(product));
        when(productRepo.findAll(any(Pageable.class))).thenReturn(productPage);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);
        var result = productService.getProducts(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Product1", result.getContent().get(0).getName());
    }

    @Test
    void testUpdateProduct() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);
        var result = productService.updateProduct(1L, productDto);
        assertNotNull(result);
        assertEquals("Product1", result.getName());
        verify(productRepo, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        when(productRepo.findById(1L)).thenReturn(Optional.empty());
        var exception = assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, productDto));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void testDeleteProduct() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        var result = productService.deleteProduct(1L);
        assertEquals("Product removed successfully", result);
        verify(productRepo, times(1)).delete(any(Product.class));
    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        when(productRepo.findById(1L)).thenReturn(Optional.empty());
        var exception = assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
        assertEquals("Product not found", exception.getMessage());
    }

}