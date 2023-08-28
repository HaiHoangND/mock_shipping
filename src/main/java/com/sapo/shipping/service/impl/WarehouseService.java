package com.sapo.shipping.service.impl;

import com.sapo.shipping.repository.WarehouseRepository;
import com.sapo.shipping.service.IWarehouseService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService implements IWarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }
}
