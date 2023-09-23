package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.dto.ProductShopDto;
import com.sapo.shipping.dto.ProductWithSumSoldProduct;
import com.sapo.shipping.dto.ShopOwnerWithNumberOfShippingOrder;
import com.sapo.shipping.entity.Product;
import com.sapo.shipping.entity.ProductShop;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.ProductMapper;
import com.sapo.shipping.mapper.ProductShopMapper;
import com.sapo.shipping.repository.ProductRepository;
import com.sapo.shipping.repository.ProductShopRepository;
import com.sapo.shipping.service.IProductService;
import com.sapo.shipping.service.IProductShopService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductShopService implements IProductShopService {
    private final ProductShopRepository productShopRepository;
    private final ProductRepository productRepository;
    private final Validator validator;
    private final ProductShopMapper mapper;

    public ProductShopService(ProductRepository productRepository,ProductShopRepository productShopRepository, Validator validator, ProductShopMapper mapper) {
        this.productShopRepository = productShopRepository;
        this.validator = validator;
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductShop> getAll() {
        return productShopRepository.findAll();
    }

    @Override
    public ProductShop getById(int id) {
        return productShopRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Product not found"));
    }

    @Override
    public Page<ProductShop> getProductShopsByShopOwnerId(Integer shopOwnerId, int pageNumber, int pageSize, String keyWord){
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return productShopRepository.getProductShopsByShopOwnerId(shopOwnerId, pageRequest, keyWord);
    }

    @Override
    public List<ProductWithSumSoldProduct> getTop10BestSellingProducts(int shopOwnerId){
        List<ProductShop> productShops = productShopRepository.getAllProductShopsByShopOwnerIdNoPage(shopOwnerId);
        List<ProductWithSumSoldProduct> productWithSumSoldProductList = new ArrayList<>();
        for(ProductShop productShop : productShops){
            ProductWithSumSoldProduct productWithSumSoldProduct = new ProductWithSumSoldProduct(productShop);
            Integer sumSoldProduct = productRepository.getSumSoldProduct(shopOwnerId, productShop.getProductCode());
            int actualSumSoldProduct = (sumSoldProduct != null) ? sumSoldProduct.intValue() : 0;
            productWithSumSoldProduct.setSumSoldProduct(actualSumSoldProduct);
            productWithSumSoldProductList.add(productWithSumSoldProduct);
        }
        productWithSumSoldProductList.sort(Comparator.comparingInt(ProductWithSumSoldProduct::getSumSoldProduct).reversed());
        List<ProductWithSumSoldProduct> top10ProductShops = productWithSumSoldProductList.stream()
                .limit(10)
                .collect(Collectors.toList());

        return top10ProductShops;
    }

    @Override
    public Page<ProductShop> getRunningOutProductShop(Integer shopOwnerId, int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return productShopRepository.getRunningOutProductShop(shopOwnerId, pageRequest);
    }

    @Override
    public Boolean checkNotExistedProductCode(Integer shopOwnerId, String productCode){
        String error = "Mã sản phẩm " + productCode + " đã tồn tại";
        if(productShopRepository.getProductShopByProductCodeAndShopOwnerId(shopOwnerId, productCode) != null){
            throw new BusinessException("400", "error", error);
        }
        return true;
    };

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ProductShop create(ProductShopDto productShopDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(productShopDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        String productCode = productShopDto.getProductCode();
        int shopOwnerId = productShopDto.getShopOwnerId();
        String error = "Mã sản phẩm " + productCode + " đã tồn tại";
        if(productShopRepository.getProductShopByProductCodeAndShopOwnerId(shopOwnerId, productCode) != null){
            throw new BusinessException("400", "error", error);
        }
        ProductShop productShop = mapper.createEntity(productShopDto);
        return productShopRepository.save(productShop);
    }

    @Override
    public List<ProductShop> getAllProductShopsByShopOwnerIdNoPage(Integer shopOwnerId){
        return productShopRepository.getAllProductShopsByShopOwnerIdNoPage(shopOwnerId);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ProductShop update(int id, ProductShopDto productShopDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(productShopDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        ProductShop productShop = productShopRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Product not found"));
        String productCode = productShopDto.getProductCode();
        int shopOwnerId = productShopDto.getShopOwnerId();
        String error = "Mã sản phẩm " + productCode + " đã tồn tại";
        ProductShop productShop1 = productShopRepository.getProductShopByProductCodeAndShopOwnerId(shopOwnerId, productCode);
        if(productShop1 != null && productShop1.getId() != id){
            throw new BusinessException("400", "error", error);
        }
        mapper.updateEntity(productShop, productShopDto);
        return productShopRepository.save(productShop);
    }

    @Override
    public Boolean delete(int id) {
        productShopRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Product not found"));
        productShopRepository.deleteById(id);
        return true;
    }
}
