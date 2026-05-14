package com.filipe.ecommerce.kafka;

import com.filipe.ecommerce.customer.CustomerResponse;
import com.filipe.ecommerce.order.PaymentMethod;
import com.filipe.ecommerce.product.PurchaseResponse;
import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {}
