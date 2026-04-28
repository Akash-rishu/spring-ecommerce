package com.akash.ecommerce.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Category filter
    List<Product> findByCategory_Id(Long categoryId);

    // Latest products
    List<Product> findByIsLatestTrue();

    // Search
    List<Product> findByProductNameContainingIgnoreCase(String name);

    // Search + category
    List<Product> findByProductNameContainingIgnoreCaseAndCategory_Id(String name, Long categoryId);

    // Price filter
    List<Product> findByProductPriceLessThanEqual(BigDecimal price);
}