package com.akash.ecommerce.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.ecommerce.entity.Category;
import com.akash.ecommerce.entity.Product;
public interface ProductRepository extends JpaRepository<Product, Long> {
     public List<Product> findByCategoryId(Category categoryId);
    public List<Product> findByIsLatestTrue();
}
