package com.akash.ecommerce.service;


import java.util.List;

import com.akash.ecommerce.entity.Cart;


public interface CartService {

    // USER - add product to cart
    Cart addToCart(Long userId, Long productId, int quantity);

    // USER - get own cart
    List<Cart> getUserCart(Long userId);

    // USER - remove only their cart item
    void removeFromCart(Long userId, Long cartId);

    // OPTIONAL (recommended) - clear full cart
    void clearCart(Long userId);
}