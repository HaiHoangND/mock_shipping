package com.sapo.shipping.service;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.dto.ProductShopDto;
import com.sapo.shipping.dto.ProductWithSumSoldProduct;
import com.sapo.shipping.entity.Product;
import com.sapo.shipping.entity.ProductShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductShopService {
    List<ProductShop> getAll();

    ProductShop getById(int id);

    ProductShop create(ProductShopDto productShopDto);

    ProductShop update(int id, ProductShopDto productShopDto);

    Boolean delete(int id);

    Page<ProductShop> getProductShopsByShopOwnerId(Integer shopOwnerId, int pageNumber, int pageSize, String keyWord);

    Page<ProductShop> getRunningOutProductShop(Integer shopOwnerId, int pageNumber, int pageSize);

    Boolean checkNotExistedProductCode(Integer shopOwnerId, String productCode);

    List<ProductShop> getAllProductShopsByShopOwnerIdNoPage(Integer shopOwnerId);
    List<ProductWithSumSoldProduct> getBestSellingProducts(int shopOwnerId, int numberOfProducts);
}
