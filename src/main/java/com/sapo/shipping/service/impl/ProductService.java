package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.entity.Product;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.ProductMapper;
import com.sapo.shipping.repository.ProductRepository;
import com.sapo.shipping.service.IProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final Validator validator;
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository, Validator validator, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Product not found"));
    }

    @Override
    public Page<Product> getProductsByShopOwnerId(Integer shopOwnerId,int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return productRepository.getProductsByShopOwnerId(shopOwnerId, pageRequest);
    };

    @Override
    public Product create(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(productDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        Product product = mapper.createEntity(productDto);
        return productRepository.save(product);

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Product update(int id, ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(productDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Product not found"));
        mapper.updateEntity(product, productDto);
        return productRepository.save(product);
    }

    @Override
    public Boolean delete(int id) {
        productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Product not found"));
        productRepository.deleteById(id);
        return true;
    }
}
