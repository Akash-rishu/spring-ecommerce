package com.akash.ecommerce.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==============================
    // NAME
    // ==============================
    @Column(nullable = false)
    private String name;

    // ==============================
    // EMAIL
    // ==============================
    @Column(nullable = false, unique = true)
    private String email;

    // ==============================
    // PASSWORD
    // ==============================
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    // ==============================
    // ROLE
    // ==============================
    @Enumerated(EnumType.STRING)
    private Role role;

    // ==============================
    // PHONE NUMBER
    // ==============================
    @Column(name = "phone_number")
    private String phoneNumber;

    // ==============================
    // CREATED TIME
    // ==============================
    @CreationTimestamp
    @Column(
            name = "created_at",
            updatable = false
    )
    private LocalDateTime createdAt;

    // ==============================
    // UPDATED TIME
    // ==============================
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ==============================
    // DEFAULT CONSTRUCTOR
    // ==============================
    public User() {
    }

    // ==============================
    // CONSTRUCTOR
    // ==============================
    public User(

            String name,

            String email,

            String password,

            String phoneNumber,

            Role role
    ) {

        this.name = name;

        this.email = email;

        this.password = password;

        this.phoneNumber = phoneNumber;

        this.role =
                (role != null)
                ? role
                : Role.USER;
    }

    // ==============================
    // GETTERS & SETTERS
    // ==============================

    public Long getId() {
        return id;
    }

    public void setId(
            Long id
    ) {
        this.id = id;
    }

    // NAME
    public String getName() {
        return name;
    }

    public void setName(
            String name
    ) {
        this.name = name;
    }

    // EMAIL
    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email
    ) {
        this.email = email;
    }

    // PASSWORD
    public String getPassword() {
        return password;
    }

    public void setPassword(
            String password
    ) {
        this.password = password;
    }

    // PHONE NUMBER
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(
            String phoneNumber
    ) {
        this.phoneNumber = phoneNumber;
    }

    // ROLE
    public Role getRole() {
        return role;
    }

    public void setRole(
            Role role
    ) {
        this.role = role;
    }

    // CREATED AT
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt = createdAt;
    }

    // UPDATED AT
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt
    ) {
        this.updatedAt = updatedAt;
    }
}