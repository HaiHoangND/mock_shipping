package com.sapo.shipping.controller;

import com.sapo.shipping.dto.OrderRouteDto;
import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.OrderRouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orderRoute")
public class OrderRouteController {
    private final OrderRouteService orderRouteService;

    public OrderRouteController(OrderRouteService orderRouteService) {
        this.orderRouteService = orderRouteService;
    }
    @GetMapping
    GeneralResponse<?> getAllOrderRoutes() {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                orderRouteService.getAll());
    }

    @GetMapping("{id}")
    GeneralResponse<?> getOrderRouteById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                orderRouteService.getById(id));
    }

    @PostMapping
    GeneralResponse<?> createOrderRoute(@RequestBody OrderRouteDto orderRouteDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                orderRouteService.create(orderRouteDto));
    }

    @PutMapping("{id}")
    GeneralResponse<?> updateOrderRoute(@PathVariable int id, @RequestBody OrderRouteDto orderRouteDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                orderRouteService.update(id, orderRouteDto));
    }
}
