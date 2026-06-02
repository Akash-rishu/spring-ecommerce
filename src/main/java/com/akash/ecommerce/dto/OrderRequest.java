package com.akash.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Long userId;

    private BigDecimal totalPrice;

    private String address;

    private String paymentMethod;

    private List<OrderItemRequest> orderItems;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest {

        private Long productId;

        private Integer quantity;

        private BigDecimal price;
    }
}