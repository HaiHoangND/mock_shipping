package com.sapo.shipping.service.impl;

import com.sapo.shipping.repository.ProductRepository;
import com.sapo.shipping.service.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

}
