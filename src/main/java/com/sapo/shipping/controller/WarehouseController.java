package com.sapo.shipping.controller;

import com.sapo.shipping.dto.WarehouseDto;
import com.sapo.shipping.entity.Warehouse;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.response.WarehouseStatsResponse;
import com.sapo.shipping.service.impl.WarehouseService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    GeneralResponse<?> getAllWarehouses() {
        return GeneralResponse.ok("success", "Successfully fetched", warehouseService.getAll());
    }

    @GetMapping("/statistic")
    WarehouseStatsResponse<?> statistic(@RequestParam(name = "warehouseId") Integer warehouseId) {
        List<Object> data = new ArrayList<>();
        Long shippingOrdersPerWarehouse = warehouseService.countShippingOrdersByWarehouseId(warehouseId);
        Long shippingOrdersBeingDelivered = warehouseService.countShippingOrdersBeingDelivered(warehouseId);
        int availableShippersPerWarehouse = warehouseService.findAvailableShippersByWarehouseId(warehouseId).size();

        Map<String, Object> statisticsMap = new HashMap<>();
        statisticsMap.put("ShippingOrders", shippingOrdersPerWarehouse);
        statisticsMap.put("Delivering", shippingOrdersBeingDelivered);
        statisticsMap.put("AvailableShippers", availableShippersPerWarehouse);

        data.add(statisticsMap);
        return WarehouseStatsResponse.ok("success",
                "Successfully fetched", data);
    }

    @GetMapping("/getAvailableShipperPerWarehouse")
    GeneralResponse<?> getAvailableShipperPerWarehouse(@RequestParam(name = "warehouseId") Integer warehouseId) {
        return GeneralResponse.ok("success",
                "Successfully fetched", warehouseService.findAvailableShippersByWarehouseId(warehouseId));
    }

    @PostMapping
    GeneralResponse<?> createWarehouse(@RequestBody WarehouseDto warehouseDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                warehouseService.create(warehouseDto));
    }

    @GetMapping("{id}")
    GeneralResponse<?> getWarehouseById(@PathVariable int id){
        return GeneralResponse.ok("success",
                "Successfully fetched",
                warehouseService.getById(id));
    }

    @DeleteMapping("{id}")
    GeneralResponse<?> deleteWarehouseById(@PathVariable int id){
        return GeneralResponse.ok("success",
                "Successfully deleted",
                warehouseService.delete(id));
    }

    @PutMapping("{id}")
    GeneralResponse<?> updateWarehouseById(@PathVariable int id, @RequestBody WarehouseDto warehouseDto){
        return GeneralResponse.ok("success",
                "Successfully updated",
                warehouseService.update(id, warehouseDto));
    }


}
