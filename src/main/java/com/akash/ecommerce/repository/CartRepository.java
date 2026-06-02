package com.akash.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.ecommerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);

    Optional<Cart> findByUserIdAndProduct_Id(Long userId, Long productId);
}