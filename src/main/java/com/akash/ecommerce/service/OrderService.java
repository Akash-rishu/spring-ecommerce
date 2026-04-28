package com.akash.ecommerce.service;

import java.util.List;

import com.akash.ecommerce.dto.OrderRequest;
import com.akash.ecommerce.dto.OrderResponse;
import com.akash.ecommerce.dto.OrderStatusUpdateRequest;

public interface OrderService {

    //  ADMIN ONLY
    List<OrderResponse> getAllOrders();

    //  USER - get only their orders
    List<OrderResponse> getOrdersByUser(Long userId);

    // USER - get specific order (with ownership check)
    OrderResponse getOrderByIdForUser(Long orderId, Long userId);

    // USER - create order (secure)
    OrderResponse createOrder(OrderRequest orderRequest, Long userId);

    // ADMIN ONLY - update status
    OrderResponse updateOrderStatus(Long orderId, OrderStatusUpdateRequest statusUpdateRequest);
}