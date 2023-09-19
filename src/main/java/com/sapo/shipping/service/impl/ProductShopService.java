package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.dto.ProductShopDto;
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
import java.util.List;

@Service
public class ProductShopService implements IProductShopService {
    private final ProductShopRepository productShopRepository;
    private final Validator validator;
    private final ProductShopMapper mapper;

    public ProductShopService(ProductShopRepository productShopRepository, Validator validator, ProductShopMapper mapper) {
        this.productShopRepository = productShopRepository;
        this.validator = validator;
        this.mapper = mapper;
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
    };

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
