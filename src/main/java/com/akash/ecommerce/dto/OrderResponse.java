package com.akash.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.akash.ecommerce.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private Long userId;

    private BigDecimal totalPrice;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String address;

    private String paymentMethod;

    private List<OrderItemResponse>
            orderItems;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {

        private Long productId;

        private String productName;

        private Integer quantity;

        private BigDecimal price;
    }
}