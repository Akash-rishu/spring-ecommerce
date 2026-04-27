package com.akash.ecommerce.service;

import java.util.List;

import com.akash.ecommerce.entity.Product;

public interface ProductService {

    Product addProduct(Product product);

    List<Product> findAllProducts();

    Product findProductById(long productId);

    Product updateProduct(long productId, Product updateProduct);

    Product deleteProduct(long productId);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByIsLatestTrue();

    void toggleLatestStatus(Long productId);
}