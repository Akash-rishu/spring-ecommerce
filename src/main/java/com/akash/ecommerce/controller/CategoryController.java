package com.akash.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.ecommerce.entity.Category;
import com.akash.ecommerce.service.CategoryService;
import com.akash.ecommerce.utils.ResponseStructure;
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // ADD CATEGORY
    @PostMapping
    public ResponseEntity<ResponseStructure<Category>> addCategory(@RequestBody Category category) {

        Category saved = categoryService.addCategory(category);

        ResponseStructure<Category> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.CREATED.value());
        res.setMessage("Category created");
        res.setData(saved);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Category>>> getAll() {

        List<Category> list = categoryService.getAllCategories();

        ResponseStructure<List<Category>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Categories fetched");
        res.setData(list);

        return ResponseEntity.ok(res);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Category>> getById(@PathVariable Long id) {

        Category category = categoryService.getCategoryById(id);

        ResponseStructure<Category> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Category found");
        res.setData(category);

        return ResponseEntity.ok(res);
    }
}