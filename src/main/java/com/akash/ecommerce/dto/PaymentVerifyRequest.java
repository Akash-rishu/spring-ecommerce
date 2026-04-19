package com.akash.ecommerce.dto;

public class PaymentVerifyRequest {
    private Long paymentId;
    private String transactionId;
    private String razorpaySignature;
    // Additional fields (like razorpayOrderId, razorpayPaymentId) if needed for
    // signature verification

    // Getters and setters

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }
}
