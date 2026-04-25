package com.akash.ecommerce.service;

import java.util.List;

import com.akash.ecommerce.dto.OrderRequest;
import com.akash.ecommerce.dto.OrderResponse;
import com.akash.ecommerce.dto.OrderStatusUpdateRequest;


public interface OrderService {
    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse updateOrderStatus(Long id, OrderStatusUpdateRequest statusUpdateRequest);
}