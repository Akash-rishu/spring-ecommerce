package com.akash.ecommerce.service;

import com.akash.ecommerce.dto.OrderRequest;
import com.akash.ecommerce.dto.OrderResponse;
import com.akash.ecommerce.dto.OrderStatusUpdateRequest;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse updateOrderStatus(Long id, OrderStatusUpdateRequest statusUpdateRequest);
}