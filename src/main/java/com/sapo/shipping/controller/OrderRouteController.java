package com.sapo.shipping.controller;

import com.sapo.shipping.dto.OrderRouteDto;
import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.entity.OrderRoute;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.OrderRouteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orderRoute")
public class OrderRouteController {
    private final OrderRouteService orderRouteService;

    public OrderRouteController(OrderRouteService orderRouteService) {
        this.orderRouteService = orderRouteService;
    }
    @GetMapping
    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getAllOrderRoutes() {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                orderRouteService.getAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getOrderRouteById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                orderRouteService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> createOrderRoute(@RequestBody OrderRouteDto orderRouteDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                orderRouteService.create(orderRouteDto));
    }

    @GetMapping("/getRouteByOrderAndRoute")
    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getRouteByOrderAndRoute(@RequestParam(name = "orderId") Integer orderId, @RequestParam(name = "routeId") Integer routeId) {
        return GeneralResponse.ok("success",
                "Successfully fetched", orderRouteService.getRouteByOrderIdAndRouteId(orderId,routeId));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> updateOrderRoute(@PathVariable int id, @RequestBody OrderRouteDto orderRouteDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                orderRouteService.update(id, orderRouteDto));
    }
}
