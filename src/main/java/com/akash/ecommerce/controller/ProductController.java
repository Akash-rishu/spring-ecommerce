package com.akash.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.ecommerce.entity.Product;
import com.akash.ecommerce.service.ProductService;
import com.akash.ecommerce.utils.ResponseStructure;


@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Add Product
    @PostMapping
    public ResponseEntity<ResponseStructure<Product>> addProduct(@RequestBody Product product) {

        Product savedProduct = productService.addProduct(product);

        ResponseStructure<Product> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Product created successfully");
        response.setData(savedProduct);
        System.out.println("POST API HIT");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //  Get All Products
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts() {

        List<Product> products = productService.findAllProducts();

        ResponseStructure<List<Product>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Products fetched successfully");
        response.setData(products);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Product>> getProductById(@PathVariable Long id) {

        Product product = productService.findProductById(id);

        ResponseStructure<Product> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Product found");
        response.setData(product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update Product
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Product>> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        Product updatedProduct = productService.updateProduct(id, product);

        ResponseStructure<Product> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Product updated successfully");
        response.setData(updatedProduct);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete Product
   @DeleteMapping("/{id}")
public ResponseEntity<ResponseStructure<String>> deleteProduct(@PathVariable Long id) {

    productService.deleteProduct(id);

    ResponseStructure<String> response = new ResponseStructure<>();
    response.setStatusCode(HttpStatus.OK.value());
    response.setMessage("Product deleted successfully");
    response.setData("Deleted");

    return new ResponseEntity<>(response, HttpStatus.OK);
}

    // Get Products by Category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponseStructure<List<Product>>> getProductsByCategory(@PathVariable Long categoryId) {

        List<Product> products = productService.findByCategoryId(categoryId);

        ResponseStructure<List<Product>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Products fetched by category");
        response.setData(products);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get Latest Products
    @GetMapping("/latest")
    public ResponseEntity<ResponseStructure<List<Product>>> getLatestProducts() {

        List<Product> products = productService.findByIsLatestTrue();

        ResponseStructure<List<Product>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Latest products fetched");
        response.setData(products);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Toggle Latest Flag
    @PutMapping("/{id}/latest")
    public ResponseEntity<ResponseStructure<String>> toggleLatest(@PathVariable Long id) {

        productService.toggleLatestStatus(id);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Latest status updated");
        response.setData("Success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
public ResponseEntity<ResponseStructure<List<Product>>> searchProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long categoryId) {

    List<Product> products = productService.searchProducts(name, categoryId);

    ResponseStructure<List<Product>> response = new ResponseStructure<>();
    response.setStatusCode(HttpStatus.OK.value());
    response.setMessage("Search results fetched");
    response.setData(products);

    return new ResponseEntity<>(response, HttpStatus.OK);
}}