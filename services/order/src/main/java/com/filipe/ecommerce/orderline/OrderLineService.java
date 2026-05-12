package com.filipe.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
    }

    public Long saveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrderLine( request );
        return repository.save( order ).getId();
    }
}
