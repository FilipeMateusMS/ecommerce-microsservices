package com.filipe.ecommerce.payment;

import com.filipe.ecommerce.customer.CustomerResponse;
import com.filipe.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Long orderId,
        String orderReference,
        CustomerResponse customer
) { }
