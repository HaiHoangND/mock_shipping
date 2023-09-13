package com.sapo.shipping.controller;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    GeneralResponse<?> getAllProducts() {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productService.getAll());
    }

    @GetMapping("{id}")
    GeneralResponse<?> getProductById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productService.getById(id));
    }

    @GetMapping("/getByShopOwnerId")
    GeneralResponse<?> getByShopOwnerId(@RequestParam(name = "ShopOwnerId") Integer shopOwnerId,@RequestParam int pageNumber,
                                        @RequestParam int pageSize) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productService.getProductsByShopOwnerId(shopOwnerId, pageNumber, pageSize));
    }

    @PostMapping
    GeneralResponse<?> createProduct(@RequestBody ProductDto productDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                productService.create(productDto));
    }

    @PutMapping("{id}")
    GeneralResponse<?> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                productService.update(id, productDto));
    }

    @DeleteMapping("{id}")
    GeneralResponse<?> deleteProductById(@PathVariable int id){
        return GeneralResponse.ok("success",
                "Successfully deleted",
                productService.delete(id));
    }
}
