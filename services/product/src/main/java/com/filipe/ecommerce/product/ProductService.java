package com.filipe.ecommerce.product;

import com.filipe.ecommerce.exceptions.ProductPurchaseException;
import com.filipe.ecommerce.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Long createProduct( ProductRequest request ) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public ProductResponse findById( Long id ) {
        return repository.findById( id )
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID:: " + id));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map( mapper::toProductResponse )
                .collect(Collectors.toList());
    }

    @Transactional( rollbackFor = ProductPurchaseException.class )
    public List<ProductPurchaseResponse> purchaseProducts( List<ProductPurchaseRequest> request )
    {
        var productIds = request.stream()
                .map( ProductPurchaseRequest::productId )
                .collect( Collectors.toSet() ); // Elimina duplicação de ids de produtos com Set

        // Obtêm e ordena pelo ID
        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size())
        {
            Set<Long> foundIds = storedProducts.stream()
                    .map(Product::getId)
                    .collect(Collectors.toSet());

            Set<Long> productsNotFound = new HashSet<>(productIds);
            productsNotFound.removeAll(foundIds);

            throw new ResourceNotFoundException( "Products with IDs %s not found".formatted( productsNotFound ) );
        }

        // Ordena pelo ID os produtos solicitados
        var sortedRequest = request.stream()
                .sorted( Comparator.comparing( ProductPurchaseRequest::productId ) )
                .toList();
        
        var response = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {

            // Os produtos estarão ordenados pelo id e o request também
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);

            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId() );
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity( newAvailableQuantity );
            repository.save( product );

            response.add( mapper.toProductPurchaseResponse( product, productRequest.quantity() ) );
        }
        return response;
    }
}
