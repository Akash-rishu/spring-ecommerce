package com.akash.ecommerce.service;

import java.util.List;

import com.akash.ecommerce.entity.Category;

public interface CategoryService {

    Category addCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);   // ✅ MUST EXIST

    void deleteCategory(Long id);
}
