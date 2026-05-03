package com.akash.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    
}