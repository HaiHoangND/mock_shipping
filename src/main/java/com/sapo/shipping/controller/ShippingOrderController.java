package com.sapo.shipping.controller;

import com.sapo.shipping.dto.ShippingOrderDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.ShippingOrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/order")
public class ShippingOrderController {
    private final ShippingOrderService shippingOrderService;

    public ShippingOrderController(ShippingOrderService shippingOrderService) {
        this.shippingOrderService = shippingOrderService;
    }

    @GetMapping
    GeneralResponse<?> getAllShippingOrders(@RequestParam int pageNumber,
                                            @RequestParam int pageSize) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                shippingOrderService.getAll(pageNumber, pageSize));
    }

    @GetMapping("{id}")
    GeneralResponse<?> getShippingOrdertById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                shippingOrderService.getById(id));
    }

    @PostMapping
    GeneralResponse<?> createShippingOrder(@RequestBody ShippingOrderDto shippingOrderDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                shippingOrderService.create(shippingOrderDto));
    }

    @PutMapping("{id}")
    GeneralResponse<?> updateShippingOrder(@PathVariable int id, @RequestBody ShippingOrderDto shippingOrderDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                shippingOrderService.update(id, shippingOrderDto));
    }

    @DeleteMapping("{id}")
    GeneralResponse<?> deleteShippingOrder(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully deleted",
                shippingOrderService.delete(id));
    }
}
