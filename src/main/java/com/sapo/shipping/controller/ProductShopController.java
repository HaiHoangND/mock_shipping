package com.sapo.shipping.controller;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.dto.ProductShopDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.ProductService;
import com.sapo.shipping.service.impl.ProductShopService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/productShop")
public class ProductShopController {
    private final ProductShopService productShopService;

    public ProductShopController(ProductShopService productShopService) {
        this.productShopService = productShopService;
    }

    @GetMapping
    GeneralResponse<?> getAllProductShops() {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productShopService.getAll());
    }

    @GetMapping("{id}")
    GeneralResponse<?> getProductShopById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productShopService.getById(id));
    }

    @GetMapping("/getByShopOwnerId")
    GeneralResponse<?> getByShopOwnerId(@RequestParam(name = "ShopOwnerId") Integer shopOwnerId,@RequestParam int pageNumber,
                                        @RequestParam int pageSize) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productShopService.getProductShopsByShopOwnerId(shopOwnerId, pageNumber, pageSize));
    }

    @PostMapping
    GeneralResponse<?> createProductShop(@RequestBody ProductShopDto productShopDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                productShopService.create(productShopDto));
    }

    @PutMapping("{id}")
    GeneralResponse<?> updateProductShop(@PathVariable int id, @RequestBody ProductShopDto productShopDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                productShopService.update(id, productShopDto));
    }

    @DeleteMapping("{id}")
    GeneralResponse<?> deleteProductShopById(@PathVariable int id){
        return GeneralResponse.ok("success",
                "Successfully deleted",
                productShopService.delete(id));
    }
}
