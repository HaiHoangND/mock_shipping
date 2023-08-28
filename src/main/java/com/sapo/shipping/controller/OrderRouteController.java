package com.sapo.shipping.controller;

import com.sapo.shipping.service.impl.OrderRouteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orderRoute")
public class OrderRouteController {
    private final OrderRouteService orderRouteService;

    public OrderRouteController(OrderRouteService orderRouteService) {
        this.orderRouteService = orderRouteService;
    }
}
