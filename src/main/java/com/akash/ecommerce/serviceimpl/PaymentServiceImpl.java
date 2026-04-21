package com.akash.ecommerce.serviceimpl;

import com.akash.ecommerce.dto.PaymentInitiateRequest;
import com.akash.ecommerce.dto.PaymentInitiateResponse;
import com.akash.ecommerce.dto.PaymentVerifyRequest;
import com.akash.ecommerce.dto.PaymentVerifyResponse;
import com.akash.ecommerce.entity.Payment;
import com.akash.ecommerce.entity.PaymentStatus;
import com.akash.ecommerce.repository.PaymentRepository;
import com.akash.ecommerce.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private RazorpayClient razorpayClient;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              @Value("${razorpay.key}") String key,
                              @Value("${razorpay.secret}") String secret) throws Exception {
        this.paymentRepository = paymentRepository;
        this.razorpayClient = new RazorpayClient(key, secret);
    }

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {
        try {
            JSONObject options = new JSONObject();
            // Amount in Razorpay is in paise
            int amountInPaise = request.getAmount().multiply(new BigDecimal(100)).intValue();
            options.put("amount", amountInPaise);
            options.put("currency", "INR");
            options.put("receipt", "order_rcptid_" + request.getOrderId());

            Order order = razorpayClient.Orders.create(options);
            // Persist payment initiation in the database
            Payment payment = new Payment();
            payment.setUserId(request.getUserId());
            payment.setOrderId(request.getOrderId());
            payment.setAmount(request.getAmount());
            // Initially status remains PENDING
            paymentRepository.save(payment);

            PaymentInitiateResponse response = new PaymentInitiateResponse();
            response.setOrderId(order.get("id"));
            response.setAmount(request.getAmount());
            response.setCurrency("INR");
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error initiating payment: " + e.getMessage());
        }
    }

    @Override
    public PaymentVerifyResponse verifyPayment(PaymentVerifyRequest request) {
        // Verify payment signature and update payment status accordingly.
        // You may use Razorpay's utility methods to verify the signature.
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // For demonstration, assuming verification is successful:
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(request.getTransactionId());
        paymentRepository.save(payment);

        PaymentVerifyResponse response = new PaymentVerifyResponse();
        response.setPaymentId(payment.getId());
        response.setStatus(payment.getStatus().name());
        return response;
    }
}