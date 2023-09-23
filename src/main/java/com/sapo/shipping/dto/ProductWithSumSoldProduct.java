package com.sapo.shipping.dto;

import com.sapo.shipping.entity.Product;
import com.sapo.shipping.entity.ProductShop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductWithSumSoldProduct {
    ProductShop productShop;
    int sumSoldProduct;

    public ProductWithSumSoldProduct(ProductShop productShop) {
        this.productShop = productShop;
    }
}
