package com.filipe.ecommerce.payment;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(
        Long id,

        @NotNull( message = "Field amount is required" )
        BigDecimal amount,

        @NotNull( message = "Method of payment amount is required" )
        PaymentMethod paymentMethod,

        @NotNull( message = "Order ID required" )
        Long orderId,

        @NotNull( message = "Order reference is required")
        String orderReference,

        @NotNull( message = "Customer is required" )
        Customer customer
) { }