package com.sapo.shipping.controller;

import com.sapo.shipping.dto.ShippingOrderDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.ShippingOrderService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/order")
public class ShippingOrderController {
    private final ShippingOrderService shippingOrderService;

    public ShippingOrderController(ShippingOrderService shippingOrderService) {
        this.shippingOrderService = shippingOrderService;
    }

    @GetMapping
    GeneralResponse<?> getAllShippingOrders(@RequestParam int pageNumber,
                                            @RequestParam int pageSize, @RequestParam(name = "orderCode", required = false) String orderCode) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                shippingOrderService.getAll(pageNumber, pageSize, orderCode));
    }

    @GetMapping("{id}")
    GeneralResponse<?> getShippingOrderById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                shippingOrderService.getById(id));
    }

    @GetMapping("/getByCode")
    GeneralResponse<?> getShippingOrderByOrderCode(@RequestParam(name = "orderCode") String orderCode) {
        return GeneralResponse.ok("success",
                "Successfully fetched", shippingOrderService.findByCode(orderCode));
    }

    @GetMapping("/getByShopOwnerId")
    GeneralResponse<?> getByShopOwnerId(@RequestParam(name = "ShopOwnerId") Integer shopOwnerId,@RequestParam int pageNumber,
                                        @RequestParam int pageSize, @RequestParam(name = "orderCode", required = false) String orderCode) {
        return GeneralResponse.ok("success",
                "Successfully fetched", shippingOrderService.getShippingOrderByShopOwner(shopOwnerId, pageNumber, pageSize, orderCode));
    }

    @GetMapping("/getTotalRevenue")
    GeneralResponse<?> getTotalRevenue(@RequestParam(name = "ShopOwnerId") Integer shopOwnerId) {
        return GeneralResponse.ok("success",
                "Successfully fetched", shippingOrderService.getTotalRevenueByShopOwnerId(shopOwnerId));
    }

    @GetMapping("/coordinatorStatistic")
    GeneralResponse<?> coordinatorStatistic() {
        return GeneralResponse.ok("success",
                "Successfully fetched", shippingOrderService.coordinatorStatistic());
    }

    @GetMapping("/shopOwnerStatistic")
    GeneralResponse<?> shopOwnerStatistic(@RequestParam(name = "shopOwnerId") Integer shopOwnerId) {
        return GeneralResponse.ok("success",
                "Successfully fetched", shippingOrderService.shopOwnerStatistic(shopOwnerId));
    }

    @GetMapping("/getTotalRevenueForDay")
    GeneralResponse<?> getTotalRevenueForDay(@RequestParam(name = "day") Integer day, @RequestParam(name = "month") Integer month,@RequestParam(name = "year") Integer year) {
        return GeneralResponse.ok("success",
                "Successfully fetched", shippingOrderService.getTotalRevenueForDay(day, month, year));
    }

    @GetMapping("/countShippingOrdersDelivering")
    GeneralResponse<?> countShippingOrdersDelivering() {
        return GeneralResponse.ok("success",
                "Successfully fetched", shippingOrderService.countShippingOrdersAreDelivering());
    }

    @GetMapping("/statisticYear")
    GeneralResponse<?> statisticYear(@RequestParam(name = "year") Integer year) {
        return GeneralResponse.ok("success",
                "Successfully fetched", shippingOrderService.statisticRevenueOfYear(year));
    }

    @GetMapping("/countSuccessfulShippingOrders")
    GeneralResponse<?> countSuccessfulShippingOrders() {
        return GeneralResponse.ok("success",
                "Successfully fetched", shippingOrderService.countSuccessfulDeliveredShippingOrders());
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
