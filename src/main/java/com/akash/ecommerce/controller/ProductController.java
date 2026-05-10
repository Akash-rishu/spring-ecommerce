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

<<<<<<< HEAD
    private final String UPLOAD_DIR = "C:/uploads/";

    // ================= ADD PRODUCT =================
    @PostMapping
    public ResponseEntity<?> addProduct(
            @RequestParam String productName,
            @RequestParam String productDescription,
            @RequestParam BigDecimal productPrice,
            @RequestParam int stock,
            @RequestParam Long categoryId,
            @RequestParam(required = false) MultipartFile image
    ) {
        try {

            // create folder if not exists
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            String fileName = null;

            // save image
            if (image != null && !image.isEmpty()) {
                fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.write(path, image.getBytes());
            }

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Product product = new Product();
            product.setProductName(productName);
            product.setProductDescription(productDescription);
            product.setProductPrice(productPrice);
            product.setStock(stock);
            product.setImage(fileName);
            product.setCategory(category);

            return ResponseEntity.ok(productRepository.save(product));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    // ================= UPDATE PRODUCT =================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam String productName,
            @RequestParam String productDescription,
            @RequestParam BigDecimal productPrice,
            @RequestParam int stock,
            @RequestParam Long categoryId,
            @RequestParam(required = false) MultipartFile image
    ) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setProductName(productName);
            product.setProductDescription(productDescription);
            product.setProductPrice(productPrice);
            product.setStock(stock);

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            product.setCategory(category);

            // update image
            if (image != null && !image.isEmpty()) {

                // delete old image
                if (product.getImage() != null) {
                    File old = new File(UPLOAD_DIR + product.getImage());
                    if (old.exists()) old.delete();
                }

                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.write(path, image.getBytes());
=======
    private final String UPLOAD_DIR =
            "D:/project/ecommerce/uploads/";

    // ADD PRODUCT
    @PostMapping
    public ResponseEntity<?> addProduct(

            @RequestParam String productName,

            @RequestParam String description,

            @RequestParam String specifications,

            @RequestParam BigDecimal productPrice,

            @RequestParam int stock,

            @RequestParam Long categoryId,

            @RequestParam(required = false)
            MultipartFile image

    ) {

        try {

            File dir = new File(
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

                Path path = Paths.get(
                        UPLOAD_DIR + fileName
                );

                Files.write(
                        path,
                        image.getBytes()
                );
            }

            Category category =
                    categoryRepository
                    .findById(categoryId)

                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Category not found"
                            )
                    );

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

            product.setStock(stock);

            product.setImage(fileName);

            product.setCategory(category);

            return ResponseEntity.ok(
                    productRepository.save(
                            product
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body("Upload failed");
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

            @RequestParam int stock,

            @RequestParam Long categoryId,

            @RequestParam(required = false)
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

            product.setStock(stock);

            Category category =
                    categoryRepository
                    .findById(categoryId)

                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Category not found"
                            )
                    );

            product.setCategory(category);

            // UPDATE IMAGE
            if (
                image != null &&
                !image.isEmpty()
            ) {

                // DELETE OLD IMAGE
                if (
                    product.getImage() != null
                ) {

                    File old =
                            new File(
                                UPLOAD_DIR
                                + product.getImage()
                            );

                    if (old.exists()) {
                        old.delete();
                    }
                }

                String fileName =
                        System.currentTimeMillis()
                        + "_"
                        + image.getOriginalFilename();

                Path path = Paths.get(
                        UPLOAD_DIR + fileName
                );

                Files.write(
                        path,
                        image.getBytes()
                );
>>>>>>> b654978 (My code)

                product.setImage(fileName);
            }

<<<<<<< HEAD
            return ResponseEntity.ok(productRepository.save(product));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Update failed");
        }
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // delete image file
        if (product.getImage() != null) {
            File file = new File(UPLOAD_DIR + product.getImage());
            if (file.exists()) file.delete();
=======
            return ResponseEntity.ok(
                    productRepository.save(
                            product
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body("Update failed");
        }
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long id
    ) {

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

            File file =
                    new File(
                        UPLOAD_DIR
                        + product.getImage()
                    );

            if (file.exists()) {
                file.delete();
            }
>>>>>>> b654978 (My code)
        }

        productRepository.delete(product);

<<<<<<< HEAD
        return ResponseEntity.ok("Deleted successfully");
=======
        return ResponseEntity.ok(
                "Deleted successfully"
        );
>>>>>>> b654978 (My code)
    }
}