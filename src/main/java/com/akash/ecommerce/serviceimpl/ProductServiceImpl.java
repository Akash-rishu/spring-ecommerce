package com.akash.ecommerce.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.akash.ecommerce.entity.Category;
import com.akash.ecommerce.entity.Product;
import com.akash.ecommerce.repository.CategoryRepository;
import com.akash.ecommerce.repository.ProductRepository;
import com.akash.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // ADD PRODUCT
    @Override
    public Product addProduct(Product product) {

        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        }

        Long categoryId = product.getCategory().getId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        product.setCategory(category);

        return productRepository.save(product);
    }

    // GET ALL
    @Override
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found");
        }

        return products;
    }

    // GET BY ID
    @Override
    public Product findProductById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    // UPDATE
    @Override
    public Product updateProduct(long productId, Product updatedProduct) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (updatedProduct.getCategory() == null || updatedProduct.getCategory().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        }

        Long categoryId = updatedProduct.getCategory().getId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setCategory(category);

        return productRepository.save(updatedProduct);
    }

    // DELETE
    @Override
    public Product deleteProduct(long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        productRepository.delete(product);

        return product;
    }

    // FILTER BY CATEGORY
    @Override
    public List<Product> findByCategoryId(Long categoryId) {

        List<Product> products = productRepository.findByCategory_Id(categoryId);

        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found for this category");
        }

        return products;
    }

    // LATEST PRODUCTS
    @Override
    public List<Product> findByIsLatestTrue() {

        List<Product> products = productRepository.findByIsLatestTrue();

        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No latest products found");
        }

        return products;
    }

    // TOGGLE LATEST
    @Override
    public void toggleLatestStatus(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.setLatest(!product.isLatest());

        productRepository.save(product);
    }

    //  IMPORTANT: SEARCH + FILTER LOGIC
    @Override
    public List<Product> searchProducts(String name, Long categoryId) {

        if (name != null && categoryId != null) {
            return productRepository
                    .findByProductNameContainingIgnoreCaseAndCategory_Id(name, categoryId);
        }

        if (name != null) {
            return productRepository
                    .findByProductNameContainingIgnoreCase(name);
        }

        if (categoryId != null) {
            return productRepository
                    .findByCategory_Id(categoryId);
        }

        return productRepository.findAll();
    }
}