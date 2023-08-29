package com.sapo.shipping.service;

import com.sapo.shipping.dto.WarehouseDto;
import com.sapo.shipping.entity.Warehouse;

import java.util.List;

public interface IWarehouseService {
    List<Warehouse> getAll();

    Warehouse getById(int id);

    Warehouse create(WarehouseDto warehouseDto);

    List<Warehouse> delete(int id);

    Warehouse update(int id, WarehouseDto warehouseDto);
}
