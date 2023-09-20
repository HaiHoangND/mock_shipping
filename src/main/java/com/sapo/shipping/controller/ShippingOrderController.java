package com.sapo.shipping.controller;

import com.sapo.shipping.dto.ShippingOrderDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.ShippingOrderService;
import org.springframework.security.access.prepost.PreAuthorize;
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
        @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
                        "or hasRole('COORDINATOR') or hasRole('ADMIN')")
        GeneralResponse<?> getAllShippingOrders(@RequestParam int pageNumber,
                        @RequestParam int pageSize,
                        @RequestParam(name = "orderCode", required = false) String orderCode) {
                return GeneralResponse.ok("success",
                                "Successfully fetched",
                                shippingOrderService.getAll(pageNumber, pageSize, orderCode));
        }

        @GetMapping("{id}")
        @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
                        "or hasRole('COORDINATOR') or hasRole('ADMIN')")
        GeneralResponse<?> getShippingOrderById(@PathVariable int id) {
                return GeneralResponse.ok("success",
                                "Successfully fetched",
                                shippingOrderService.getById(id));
        }

        @GetMapping("/getByCode")
        @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
                        "or hasRole('COORDINATOR') or hasRole('ADMIN')")
        GeneralResponse<?> getShippingOrderByOrderCode(@RequestParam(name = "orderCode") String orderCode) {
                return GeneralResponse.ok("success",
                                "Successfully fetched", shippingOrderService.findByCode(orderCode));
        }

        @GetMapping("/getByShopOwnerId")
        @PreAuthorize("hasRole('SHOP')")
        GeneralResponse<?> getByShopOwnerId(@RequestParam(name = "ShopOwnerId") Integer shopOwnerId,
                        @RequestParam int pageNumber,
                        @RequestParam int pageSize,
                        @RequestParam(name = "orderCode", required = false) String orderCode) {
                return GeneralResponse.ok("success",
                                "Successfully fetched", shippingOrderService.getShippingOrderByShopOwner(shopOwnerId,
                                                pageNumber, pageSize, orderCode));
        }

        @GetMapping("/getTotalRevenue")
        @PreAuthorize("hasRole('ADMIN')or hasRole('COORDINATOR') or hasRole('SHOP')")
        GeneralResponse<?> getTotalRevenue(@RequestParam(name = "ShopOwnerId") Integer shopOwnerId) {
                return GeneralResponse.ok("success",
                                "Successfully fetched", shippingOrderService.getTotalRevenueByShopOwnerId(shopOwnerId));
        }

        @GetMapping("/coordinatorStatistic")
        @PreAuthorize("hasRole('COORDINATOR')")
        GeneralResponse<?> coordinatorStatistic() {
                return GeneralResponse.ok("success",
                                "Successfully fetched", shippingOrderService.coordinatorStatistic());
        }

        @GetMapping("/shopOwnerStatistic")
        @PreAuthorize("hasRole('SHOP')")
        GeneralResponse<?> shopOwnerStatistic(@RequestParam(name = "shopOwnerId") Integer shopOwnerId) {
                return GeneralResponse.ok("success",
                                "Successfully fetched", shippingOrderService.shopOwnerStatistic(shopOwnerId));
        }

        @GetMapping("/getTotalRevenueForDay")
        @PreAuthorize("hasRole('ADMIN')")
        GeneralResponse<?> getTotalRevenueForDay(@RequestParam(name = "day") Integer day,
                        @RequestParam(name = "month") Integer month, @RequestParam(name = "year") Integer year) {
                return GeneralResponse.ok("success",
                                "Successfully fetched", shippingOrderService.getTotalRevenueForDay(day, month, year));
        }

        @GetMapping("/countShippingOrdersDelivering")
        @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
                        "or hasRole('COORDINATOR') or hasRole('ADMIN')")
        GeneralResponse<?> countShippingOrdersDelivering() {
                return GeneralResponse.ok("success",
                                "Successfully fetched", shippingOrderService.countShippingOrdersAreDelivering());
        }

        @GetMapping("/statisticMonthForAdmin")
        @PreAuthorize("hasRole('ADMIN')")
        GeneralResponse<?> statisticMonth(@RequestParam(name = "month") Integer month,
                        @RequestParam(name = "year") Integer year) {
                return GeneralResponse.ok("success",
                                "Successfully fetched", shippingOrderService.statisticRevenueOfYear(month, year));
        }

        @GetMapping("/statisticMonthForShop")
        @PreAuthorize("hasRole('SHOP')")
        GeneralResponse<?> statisticMonth(@RequestParam(name = "month") Integer month,
                        @RequestParam(name = "year") Integer year, @RequestParam Integer shopOwnerId) {
                return GeneralResponse.ok("success",
                                "Successfully fetched",
                                shippingOrderService.statisticRevenueOfMonth(month, year, shopOwnerId));
        }

        @GetMapping("/countSuccessfulShippingOrders")
        @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
                        "or hasRole('COORDINATOR') or hasRole('ADMIN')")
        GeneralResponse<?> countSuccessfulShippingOrders() {
                return GeneralResponse.ok("success",
                                "Successfully fetched", shippingOrderService.countSuccessfulDeliveredShippingOrders());
        }

        @PostMapping
        @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
                "or hasRole('COORDINATOR') or hasRole('ADMIN')")
        GeneralResponse<?> createShippingOrder(@RequestBody ShippingOrderDto shippingOrderDto) {
                return GeneralResponse.ok("success",
                                "Successfully created",
                                shippingOrderService.create(shippingOrderDto));
        }

        @PutMapping("{id}")
        @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
                        "or hasRole('COORDINATOR') or hasRole('ADMIN')")
        GeneralResponse<?> updateShippingOrder(@PathVariable int id, @RequestBody ShippingOrderDto shippingOrderDto) {
                return GeneralResponse.ok("success",
                                "Successfully created",
                                shippingOrderService.update(id, shippingOrderDto));
        }

        @DeleteMapping("{id}")
        @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
                        "or hasRole('COORDINATOR') or hasRole('ADMIN')")
        GeneralResponse<?> deleteShippingOrder(@PathVariable int id) {
                return GeneralResponse.ok("success",
                                "Successfully deleted",
                                shippingOrderService.delete(id));
        }
}
