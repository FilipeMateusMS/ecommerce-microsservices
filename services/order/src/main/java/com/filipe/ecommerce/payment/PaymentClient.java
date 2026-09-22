package com.filipe.ecommerce.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient( name = "product-service" )
public interface PaymentClient {

    @PostMapping
    Long requestOrderPayment( @RequestBody PaymentRequest request );
}
