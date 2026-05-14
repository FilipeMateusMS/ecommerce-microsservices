package com.filipe.ecommerce.order;

import com.filipe.ecommerce.customer.CustomerClient;
import com.filipe.ecommerce.exception.BusinessException;
import com.filipe.ecommerce.kafka.OrderConfirmation;
import com.filipe.ecommerce.kafka.OrderProducer;
import com.filipe.ecommerce.orderline.OrderLineRequest;
import com.filipe.ecommerce.orderline.OrderLineService;
import com.filipe.ecommerce.product.ProductClient;
import com.filipe.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    //private final PaymentClient paymentClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    public Long createOrder(OrderRequest request) {

        // Verifica se existe o cliente ( usando FeignClient )
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        // Faz o registro da compra no serviço de produto ( usando RestTemplate )
        var purchasedProducts = productClient.purchaseProducts( request.products() );

        var order = this.repository.save( mapper.toOrder( request ) );

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

//        var paymentRequest = new PaymentRequest(
//                request.amount(),
//                request.paymentMethod(),
//                order.getId(),
//                order.getReference(),
//                customer
//        );
//        paymentClient.requestOrderPayment( paymentRequest ); // Inicia o processo de pagamento

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map( this.mapper::fromOrder )
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Long id) {
        return this.repository.findById( id )
                .map(this.mapper::fromOrder )
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }

}
