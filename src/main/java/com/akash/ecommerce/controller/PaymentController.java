package com.akash.ecommerce.controller;

import com.akash.ecommerce.dto.PaymentInitiateRequest;
import com.akash.ecommerce.dto.PaymentInitiateResponse;
import com.akash.ecommerce.dto.PaymentVerifyRequest;
import com.akash.ecommerce.dto.PaymentVerifyResponse;
import com.akash.ecommerce.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentInitiateResponse> initiatePayment(@RequestBody PaymentInitiateRequest request) {
        PaymentInitiateResponse response = paymentService.initiatePayment(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<PaymentVerifyResponse> verifyPayment(@RequestBody PaymentVerifyRequest request) {
        PaymentVerifyResponse response = paymentService.verifyPayment(request);
        return ResponseEntity.ok(response);
    }
}
