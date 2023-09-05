package com.sapo.shipping.service;

import com.sapo.shipping.dto.WarehouseDto;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import com.sapo.shipping.entity.Warehouse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IWarehouseService {
    List<Warehouse> getAll();

    Warehouse getById(int id);

    Warehouse create(WarehouseDto warehouseDto);

    List<Warehouse> delete(int id);

    Warehouse update(int id, WarehouseDto warehouseDto);

    List<User> findAvailableShippersByWarehouseId(Integer warehouseId);

    Long countShippingOrdersBeingDelivered(Integer warehouseId);

    List<ShippingOrder> getAllShippingOrdersByWarehouseId(Integer warehouseId);

    List<User> getAllUsersByWarehouseId(Integer warehouseId);
}
