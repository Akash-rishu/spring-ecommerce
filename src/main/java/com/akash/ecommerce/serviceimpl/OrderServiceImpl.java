package com.akash.ecommerce.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.akash.ecommerce.dto.OrderRequest;
import com.akash.ecommerce.dto.OrderResponse;
import com.akash.ecommerce.dto.OrderStatusUpdateRequest;
import com.akash.ecommerce.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public List<OrderResponse> getAllOrders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderById'");
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, OrderStatusUpdateRequest statusUpdateRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus'");
    }

}
