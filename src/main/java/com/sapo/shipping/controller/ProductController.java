package com.sapo.shipping.controller;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getAllProducts() {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productService.getAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getProductById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                productService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> createProduct(@RequestBody ProductDto productDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                productService.create(productDto));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                productService.update(id, productDto));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> deleteProductById(@PathVariable int id){
        return GeneralResponse.ok("success",
                "Successfully deleted",
                productService.delete(id));
    }
}
