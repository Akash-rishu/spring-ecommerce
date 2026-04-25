package com.akash.ecommerce.serviceimpl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.akash.ecommerce.dto.PaymentInitiateRequest;
import com.akash.ecommerce.dto.PaymentInitiateResponse;
import com.akash.ecommerce.dto.PaymentVerifyRequest;
import com.akash.ecommerce.dto.PaymentVerifyResponse;
import com.akash.ecommerce.entity.Payment;
import com.akash.ecommerce.entity.PaymentStatus;
import com.akash.ecommerce.repository.PaymentRepository;
import com.akash.ecommerce.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {
        // Create payment record with PENDING status
        Payment payment = new Payment();
        payment.setUserId(request.getUserId());
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.PENDING);

        Payment saved = paymentRepository.save(payment);

        // Simulate processing and mark as SUCCESS with a generated transaction id
        String txnId = UUID.randomUUID().toString();
        saved.setTransactionId(txnId);
        saved.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(saved);

        // ...existing code...
        PaymentInitiateResponse response = new PaymentInitiateResponse();
        // Keep response fields consistent with previous implementation:
          response.setOrderId(saved.getOrderId());
          response.setOrderId(String.valueOf(saved.getOrderId()));
        response.setAmount(saved.getAmount());
        response.setCurrency("INR");
       
        return response;
//
    }

    @Override
    public PaymentVerifyResponse verifyPayment(PaymentVerifyRequest request) {
        Optional<Payment> opt = paymentRepository.findById(request.getPaymentId());
        if (opt.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }

        Payment payment = opt.get();

        // Simple verification: check transaction id if provided, else assume success
        if (request.getTransactionId() != null && !request.getTransactionId().isBlank()) {
            if (!request.getTransactionId().equals(payment.getTransactionId())) {
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
                PaymentVerifyResponse respFail = new PaymentVerifyResponse();
                respFail.setPaymentId(payment.getId());
                respFail.setStatus(payment.getStatus().name());
                return respFail;
            }
        }

        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        PaymentVerifyResponse resp = new PaymentVerifyResponse();
        resp.setPaymentId(payment.getId());
        resp.setStatus(payment.getStatus().name());
        return resp;
    }
}