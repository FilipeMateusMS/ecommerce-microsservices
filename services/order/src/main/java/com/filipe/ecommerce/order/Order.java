package com.filipe.ecommerce.order;

import com.filipe.ecommerce.orderline.OrderLine;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners( AuditingEntityListener.class )
@Table( name = "customer_order" )
public class Order {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String reference;

    private BigDecimal totalAmount; // preço total

    @Enumerated( STRING )
    private PaymentMethod paymentMethod;

    private String customerId; // referência ao id do customer que irá estar no serviço de customer

    @OneToMany( mappedBy = "order" )
    private List<OrderLine> orderLines;

    @CreatedDate
    @Column( updatable = false, nullable = false )
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( insertable = false )
    private LocalDateTime lastModifiedDate;

}
