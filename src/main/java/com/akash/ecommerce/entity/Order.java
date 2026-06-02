package com.akash.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Order {

    @Id
    @GeneratedValue(
            strategy =
            GenerationType.IDENTITY
    )
    private Long id;

    // =========================
    // USER
    // =========================
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    // =========================
    // TOTAL PRICE
    // =========================
    @Column(
            name = "total_price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal totalPrice;

    // =========================
    // SHIPPING ADDRESS
    // =========================
    @Column(
            name = "address",
            length = 1000
    )
    private String address;

    // =========================
    // PAYMENT METHOD
    // =========================
    @Column(
            name = "payment_method",
            length = 100
    )
    private String paymentMethod;

    // =========================
    // ORDER STATUS
    // =========================
    @Enumerated(
            EnumType.STRING
    )
    @Column(
            nullable = false,
            length = 50
    )
    @Builder.Default
    private OrderStatus status =
            OrderStatus.PENDING;

    // =========================
    // CREATED AT
    // =========================
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    // =========================
    // UPDATED AT
    // =========================
    @Column(
            name = "updated_at"
    )
    private LocalDateTime updatedAt;

    // =========================
    // ORDER ITEMS
    // =========================
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<OrderItem> orderItems =
            new ArrayList<>();

    // =========================
    // AUTO CREATE TIMESTAMP
    // =========================
    @PrePersist
    public void prePersist() {

        createdAt =
                LocalDateTime.now();

        updatedAt =
                LocalDateTime.now();

        // DEFAULT STATUS
        if (status == null) {

            status =
                    OrderStatus.PENDING;
        }
    }

    // =========================
    // AUTO UPDATE TIMESTAMP
    // =========================
    @PreUpdate
    public void preUpdate() {

        updatedAt =
                LocalDateTime.now();
    }
}