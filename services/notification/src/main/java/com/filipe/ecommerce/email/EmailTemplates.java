package com.filipe.ecommerce.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTemplates {

    PAYMENT_CONFIRMATION( "payment-confirmation.html", "Pagamento processado com sucesso" ),
    ORDER_CONFIRMATION( "order-confirmation.html", "Pedido confirmado" );

    private final String template;
    private final String subject;
}
