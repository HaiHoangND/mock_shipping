package com.sapo.shipping.service;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();

    Product getById(int id);

    Product create(ProductDto productDto);

    Product update(int id, ProductDto productDto);

    List<Product> delete(int id);
}
