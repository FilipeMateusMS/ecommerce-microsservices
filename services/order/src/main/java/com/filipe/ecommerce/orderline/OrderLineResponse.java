package com.filipe.ecommerce.orderline;

public record OrderLineResponse(
        Long id,
        double quantity
) { }