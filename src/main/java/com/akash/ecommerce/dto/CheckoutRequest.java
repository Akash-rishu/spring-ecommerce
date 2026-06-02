package com.akash.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {

    // DELIVERY ADDRESS
    private String address;

    // PAYMENT METHOD
    // COD / UPI
    private String paymentMethod;
}