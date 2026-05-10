package com.akash.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // USER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // TOTAL PRICE
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    // SHIPPING ADDRESS
    @Column(name = "address")
    private String address;

    // PAYMENT METHOD
    @Column(name = "payment_method")
    private String paymentMethod;

    // ORDER STATUS
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    // CREATED TIME
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // UPDATED TIME
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ORDER ITEMS
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<OrderItem> orderItems;

    // AUTO CREATE TIMESTAMP
    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        updatedAt = createdAt;
    }

    // AUTO UPDATE TIMESTAMP
    @PreUpdate
    public void preUpdate() {

        updatedAt = LocalDateTime.now();
    }
}