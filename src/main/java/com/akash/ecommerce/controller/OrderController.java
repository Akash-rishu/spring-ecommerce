package com.akash.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.ecommerce.dto.CheckoutRequest;
import com.akash.ecommerce.dto.OrderRequest;
import com.akash.ecommerce.dto.OrderResponse;
import com.akash.ecommerce.dto.OrderStatusUpdateRequest;
import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.UserRepository;
import com.akash.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    // ADMIN ONLY - Get all orders
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    // USER - Get own orders
    @GetMapping("/my")
    public List<OrderResponse> getMyOrders() {
        User user = getLoggedInUser();
        return orderService.getOrdersByUser(user.getId());
    }

    // USER - Get own order by id
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        User user = getLoggedInUser();
        return orderService.getOrderByIdForUser(id, user.getId());
    }

    // USER - Place order (NO userId from request)
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        User user = getLoggedInUser();
        return orderService.createOrder(orderRequest, user.getId());
    }

    // ADMIN ONLY - Update order status
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public OrderResponse updateOrderStatus(@PathVariable Long id,
            @RequestBody OrderStatusUpdateRequest statusUpdateRequest) {

        return orderService.updateOrderStatus(id, statusUpdateRequest);
    }

    // COMMON METHOD (IMPORTANT)
    private User getLoggedInUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @PostMapping("/checkout")
    public OrderResponse checkout(@RequestBody CheckoutRequest request) {

        User user = getLoggedInUser();

        return orderService.checkout(
                user.getId(),
                request
        );
    }
}