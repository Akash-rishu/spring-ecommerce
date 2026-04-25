package com.akash.ecommerce.service;

import com.akash.ecommerce.dto.PaymentInitiateRequest;
import com.akash.ecommerce.dto.PaymentInitiateResponse;
import com.akash.ecommerce.dto.PaymentVerifyRequest;
import com.akash.ecommerce.dto.PaymentVerifyResponse;

public interface PaymentService {
    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request);

    PaymentVerifyResponse verifyPayment(PaymentVerifyRequest request);
}
