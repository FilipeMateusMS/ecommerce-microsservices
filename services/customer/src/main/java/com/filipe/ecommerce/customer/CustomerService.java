package com.filipe.ecommerce.customer;

import com.filipe.ecommerce.exceptions.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer( CustomerRequest request) {
        var customer = repository.save( mapper.toCustomer( request ) );
        return customer.getId();
    }

    public void updateCustomer( CustomerRequest request ) {
        var customer = repository.findById( request.id() )
                .orElseThrow( () -> new CustomerNotFoundException(
                        format( "Cannot update customer with ID:: %s", request.id() ) ) );
        mergeCustomer( customer, request );
        repository.save( customer );
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstName( request.firstname() );
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return  this.repository.findAll()
                .stream()
                .map( mapper::fromCustomer)
                .collect( Collectors.toList());
    }

    public CustomerResponse findById(String id) {
        return repository.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("No customer found with the provided ID: %s", id)));
    }

    public boolean existsById(String id) {
        return repository.findById(id)
                .isPresent();
    }
    public void deleteCustomer(String id) {
        repository.deleteById(id);
    }
}
