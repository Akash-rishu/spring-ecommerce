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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @ManyToOne
    @JoinColumn(name = "category_categoryId")
    private Category category;

    // LATEST PRODUCT
    private boolean isLatest = false;

    // CREATED AT
    @CreationTimestamp
    private LocalDateTime createdAt;

    // UPDATED AT
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

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
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(
            BigDecimal productPrice
    ) {
        this.productPrice = productPrice;
    }

    public Integer getStock() {
        return stock;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(
            Category category
    ) {
        this.category = category;
    }

    public boolean isLatest() {
        return isLatest;
    }

    public void setLatest(
            boolean latest
    ) {
        isLatest = latest;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt
    ) {
        this.updatedAt = updatedAt;
    }
}