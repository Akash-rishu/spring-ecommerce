package com.akash.ecommerce.controller;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.akash.ecommerce.entity.Category;
import com.akash.ecommerce.entity.Product;
import com.akash.ecommerce.repository.CategoryRepository;
import com.akash.ecommerce.repository.ProductRepository;
import com.akash.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    private final String UPLOAD_DIR =
            "D:/project/ecommerce/uploads/";

    // ADD PRODUCT
    @PostMapping
    public ResponseEntity<?> addProduct(

            @RequestParam String productName,

            @RequestParam String description,

            @RequestParam String specifications,

            @RequestParam BigDecimal productPrice,

            @RequestParam Integer stock,

            @RequestParam Long categoryId,

            @RequestParam(
                    value = "image",
                    required = false
            )
            MultipartFile image

    ) {

        try {

            // CREATE DIRECTORY
            File dir =
                    new File(
                            UPLOAD_DIR
                    );

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = null;

            // SAVE IMAGE
            if (
                image != null &&
                !image.isEmpty()
            ) {

                fileName =
                        System.currentTimeMillis()
                        + "_"
                        + image.getOriginalFilename();

                Path path =
                        Paths.get(
                                UPLOAD_DIR
                                + fileName
                        );

                Files.write(
                        path,
                        image.getBytes()
                );
            }

            // FIND CATEGORY
            Category category =
                    categoryRepository
                    .findById(categoryId)

                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Category not found"
                            )
                    );

            // CREATE PRODUCT
            Product product =
                    new Product();

            product.setProductName(
                    productName
            );

            product.setDescription(
                    description
            );

            product.setSpecifications(
                    specifications
            );

            product.setProductPrice(
                    productPrice
            );

            product.setStock(
                    stock
            );

            product.setImage(
                    fileName
            );

            product.setCategory(
                    category
            );

            Product savedProduct =
                    productRepository.save(
                            product
                    );

            return ResponseEntity.ok(
                    savedProduct
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                        "Upload failed: "
                        + e.getMessage()
                    );
        }
    }

    // GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<List<Product>>
    getAllProducts() {

        return ResponseEntity.ok(
                productService
                .findAllProducts()
        );
    }

    // GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Product>
    getProductById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productService
                .findProductById(id)
        );
    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(

            @PathVariable Long id,

            @RequestParam String productName,

            @RequestParam String description,

            @RequestParam String specifications,

            @RequestParam BigDecimal productPrice,

            @RequestParam Integer stock,

            @RequestParam Long categoryId,

            @RequestParam(
                    value = "image",
                    required = false
            )
            MultipartFile image

    ) {

        try {

            Product product =
                    productRepository
                    .findById(id)

                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found"
                            )
                    );

            product.setProductName(
                    productName
            );

            product.setDescription(
                    description
            );

            product.setSpecifications(
                    specifications
            );

            product.setProductPrice(
                    productPrice
            );

            product.setStock(
                    stock
            );

            // CATEGORY
            Category category =
                    categoryRepository
                    .findById(categoryId)

                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Category not found"
                            )
                    );

            product.setCategory(
                    category
            );

            // UPDATE IMAGE
            if (
                image != null &&
                !image.isEmpty()
            ) {

                // DELETE OLD IMAGE
                if (
                    product.getImage() != null
                ) {

                    File oldImage =
                            new File(
                                    UPLOAD_DIR
                                    + product.getImage()
                            );

                    if (
                        oldImage.exists()
                    ) {

                        oldImage.delete();
                    }
                }

                // SAVE NEW IMAGE
                String fileName =
                        System.currentTimeMillis()
                        + "_"
                        + image.getOriginalFilename();

                Path path =
                        Paths.get(
                                UPLOAD_DIR
                                + fileName
                        );

                Files.write(
                        path,
                        image.getBytes()
                );

                product.setImage(
                        fileName
                );
            }

            Product updatedProduct =
                    productRepository.save(
                            product
                    );

            return ResponseEntity.ok(
                    updatedProduct
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                        "Update failed: "
                        + e.getMessage()
                    );
        }
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long id
    ) {

        try {

            Product product =
                    productRepository
                    .findById(id)

                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found"
                            )
                    );

            // DELETE IMAGE
            if (
                product.getImage() != null
            ) {

                File imageFile =
                        new File(
                                UPLOAD_DIR
                                + product.getImage()
                        );

                if (
                    imageFile.exists()
                ) {

                    imageFile.delete();
                }
            }

            productRepository.delete(
                    product
            );

            return ResponseEntity.ok(
                    "Product deleted successfully"
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                        "Delete failed: "
                        + e.getMessage()
                    );
        }
    }
}