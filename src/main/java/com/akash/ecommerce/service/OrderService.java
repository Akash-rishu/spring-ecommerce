package com.akash.ecommerce.service;

import java.util.List;

import com.akash.ecommerce.dto.CheckoutRequest;
import com.akash.ecommerce.dto.OrderRequest;
import com.akash.ecommerce.dto.OrderResponse;
import com.akash.ecommerce.dto.OrderStatusUpdateRequest;

public interface OrderService {

    // ==============================
    // ADMIN - GET ALL ORDERS
    // ==============================
    List<OrderResponse> getAllOrders();

    // ==============================
    // USER - GET OWN ORDERS
    // ==============================
    List<OrderResponse> getOrdersByUser(
            Long userId
    );

    // ==============================
    // USER - GET ORDER BY ID
    // ==============================
    OrderResponse getOrderByIdForUser(

            Long orderId,

            Long userId
    );

    // ==============================
    // USER - CREATE ORDER
    // ==============================
    OrderResponse createOrder(

            OrderRequest orderRequest,

            Long userId
    );

    // ==============================
    // USER - CHECKOUT
    // ==============================
    OrderResponse checkout(

            Long userId,

            CheckoutRequest request
    );

    // ==============================
    // ADMIN - UPDATE STATUS
    // ==============================
    OrderResponse updateOrderStatus(

            Long orderId,

            OrderStatusUpdateRequest
                    statusUpdateRequest
    );
}