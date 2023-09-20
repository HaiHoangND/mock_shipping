package com.sapo.shipping.controller;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.dto.ProductShopDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.ProductService;
import com.sapo.shipping.service.impl.ProductShopService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/productShop")
public class ProductShopController {
    private final ProductShopService productShopService;

    public ProductShopController(ProductShopService productShopService) {
        this.productShopService = productShopService;
    }

    @GetMapping
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    GeneralResponse<?> getAllProductShops() {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productShopService.getAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    GeneralResponse<?> getProductShopById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productShopService.getById(id));
    }

    @GetMapping("/getByShopOwnerId")
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    GeneralResponse<?> getByShopOwnerId(@RequestParam(name = "ShopOwnerId") Integer shopOwnerId,@RequestParam int pageNumber,
                                        @RequestParam int pageSize, @RequestParam(required = false) String keyWord) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productShopService.getProductShopsByShopOwnerId(shopOwnerId, pageNumber, pageSize, keyWord));
    }

    @GetMapping("/getByShopOwnerIdNoPage")
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    GeneralResponse<?> getByShopOwnerIdNoPage(@RequestParam(name = "ShopOwnerId") Integer shopOwnerId) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productShopService.getAllProductShopsByShopOwnerIdNoPage(shopOwnerId));
    }

    @GetMapping("/checkNotExistedProductCode")
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    GeneralResponse<?> checkNotExistedProductCode(@RequestParam(name = "ShopOwnerId") Integer shopOwnerId,@RequestParam(name = "productCode") String productCode) {
        try {
            return GeneralResponse.ok("success", "Successfully fetched", productShopService.checkNotExistedProductCode(shopOwnerId, productCode));
        }catch (Exception e){
            return GeneralResponse.failed("failed", e.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    GeneralResponse<?> createProductShop(@RequestBody ProductShopDto productShopDto) {
        try {
            return GeneralResponse.ok("success", "Successfully created", productShopService.create(productShopDto));
        }catch (Exception e){
            return GeneralResponse.failed("failed", e.getMessage());
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    GeneralResponse<?> updateProductShop(@PathVariable int id, @RequestBody ProductShopDto productShopDto) {
        try{
            return GeneralResponse.ok("success", "Successfully created", productShopService.update(id, productShopDto));
        }catch (Exception e){
            return GeneralResponse.failed("failed", e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    GeneralResponse<?> deleteProductShopById(@PathVariable int id){
        return GeneralResponse.ok("success",
                "Successfully deleted",
                productShopService.delete(id));
    }
}
