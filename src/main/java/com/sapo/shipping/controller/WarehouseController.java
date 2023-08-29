package com.sapo.shipping.controller;

import com.sapo.shipping.dto.WarehouseDto;
import com.sapo.shipping.entity.Warehouse;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.WarehouseService;
import org.springframework.web.bind.annotation.*;


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
