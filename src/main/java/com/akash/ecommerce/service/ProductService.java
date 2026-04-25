package com.akash.ecommerce.service;

import java.util.List;

import com.akash.ecommerce.entity.Category;
import com.akash.ecommerce.entity.Product;

public interface ProductService {

    public Product addProduct(Product product);

    public List<Product> findAllProduct();

    public Product findByProductId(long productId);

    public Product updateByProductId(long productId, Product updateProduct);

    public Product deleteByProductId(long productId);

    public List<Product> findByCategoryId(Category categoryId);

    public List<Product> findByIsLatestTrue();

    public void toggleLatestStatus(Long productId);

}
