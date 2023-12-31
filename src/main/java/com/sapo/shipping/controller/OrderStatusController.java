package com.sapo.shipping.controller;

import com.sapo.shipping.dto.OrderStatusDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.OrderStatusService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/orderStatus")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getOrderStatusById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                orderStatusService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> createOrderStatus(@RequestBody OrderStatusDto orderStatusDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                orderStatusService.create(orderStatusDto));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> updateOrderStatus(@PathVariable int id, @RequestBody OrderStatusDto orderStatusDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                orderStatusService.update(id, orderStatusDto));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> deleteOrderStatusById(@PathVariable int id){
        return GeneralResponse.ok("success",
                "Successfully deleted",
                orderStatusService.delete(id));
    }
}
