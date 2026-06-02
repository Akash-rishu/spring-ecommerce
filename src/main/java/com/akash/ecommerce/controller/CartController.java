package com.akash.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.ecommerce.entity.Cart;
import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.UserRepository;
import com.akash.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    // ADD TO CART
    @PostMapping
    public Cart addToCart(@RequestParam Long productId,
                          @RequestParam int quantity) {

        User user = getLoggedInUser();

        return cartService.addToCart(user.getId(), productId, quantity);
    }

    // GET CART
    @GetMapping
    public List<Cart> getCart() {

        User user = getLoggedInUser();

        return cartService.getUserCart(user.getId());
    }

    // REMOVE ITEM
    @DeleteMapping("/{cartId}")
    public void remove(@PathVariable Long cartId) {

        User user = getLoggedInUser();

        cartService.removeFromCart(user.getId(), cartId);
    }

    private User getLoggedInUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/{cartId}")
public Cart updateQuantity(@PathVariable Long cartId,
    @RequestParam int quantity) {

    User user = getLoggedInUser();
    return cartService.updateQuantity(cartId, quantity);
}
}