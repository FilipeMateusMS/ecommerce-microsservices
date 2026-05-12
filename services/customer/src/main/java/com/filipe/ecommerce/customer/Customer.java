package com.filipe.ecommerce.customer;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder // Padrão builder para construir os objetos
@Getter
@Setter
@Document
public class Customer {

    @Id
    private String id; // O Mongo db irá gerar a chave
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
