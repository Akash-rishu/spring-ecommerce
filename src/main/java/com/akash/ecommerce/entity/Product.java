package com.akash.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
<<<<<<< HEAD
=======

>>>>>>> b654978 (My code)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @Column(nullable = false)
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String productDescription;

    @Column(nullable = false)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Integer stock;

    private String image;

=======
    // PRODUCT NAME
    @Column(nullable = false)
    private String productName;

    // DESCRIPTION
    @Column(columnDefinition = "TEXT")
    private String description;

    // SPECIFICATIONS
    @Column(length = 3000)
    private String specifications;

    // PRICE
    @Column(nullable = false)
    private BigDecimal productPrice;

    // STOCK
    @Column(nullable = false)
    private Integer stock;

    // IMAGE
    private String image;

    // CATEGORY
>>>>>>> b654978 (My code)
    @ManyToOne
    @JoinColumn(name = "category_categoryId")
    private Category category;

<<<<<<< HEAD
    private boolean isLatest = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
=======
    // LATEST PRODUCT
    private boolean isLatest = false;

    // CREATED AT
    @CreationTimestamp
    private LocalDateTime createdAt;

    // UPDATED AT
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ================= GETTERS & SETTERS =================
>>>>>>> b654978 (My code)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

<<<<<<< HEAD
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
=======
    public void setProductName(
            String productName
    ) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description
    ) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(
            String specifications
    ) {
        this.specifications = specifications;
>>>>>>> b654978 (My code)
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

<<<<<<< HEAD
    public void setProductPrice(BigDecimal productPrice) {
=======
    public void setProductPrice(
            BigDecimal productPrice
    ) {
>>>>>>> b654978 (My code)
        this.productPrice = productPrice;
    }

    public Integer getStock() {
        return stock;
    }

<<<<<<< HEAD
    public void setStock(Integer stock) {
        this.stock = stock;
    }

=======
    public void setStock(
            Integer stock
    ) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(
            String image
    ) {
        this.image = image;
    }

>>>>>>> b654978 (My code)
    public Category getCategory() {
        return category;
    }

<<<<<<< HEAD
    public void setCategory(Category category) {
=======
    public void setCategory(
            Category category
    ) {
>>>>>>> b654978 (My code)
        this.category = category;
    }

    public boolean isLatest() {
        return isLatest;
    }

<<<<<<< HEAD
    public void setLatest(boolean latest) {
=======
    public void setLatest(
            boolean latest
    ) {
>>>>>>> b654978 (My code)
        isLatest = latest;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

<<<<<<< HEAD
    public void setCreatedAt(LocalDateTime createdAt) {
=======
    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
>>>>>>> b654978 (My code)
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

<<<<<<< HEAD
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
=======
    public void setUpdatedAt(
            LocalDateTime updatedAt
    ) {
        this.updatedAt = updatedAt;
    }
}
>>>>>>> b654978 (My code)
