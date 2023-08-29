package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.WarehouseDto;
import com.sapo.shipping.entity.Warehouse;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.WarehouseMapper;
import com.sapo.shipping.repository.WarehouseRepository;
import com.sapo.shipping.service.IWarehouseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService implements IWarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper mapper;

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseMapper mapper) {
        this.warehouseRepository = warehouseRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }

    @Override
    public Warehouse getById(int id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Warehouse not found"));
    }

    @Override
    public Warehouse create(WarehouseDto warehouseDto) {
        List<String> errors = new ArrayList<>();
        Warehouse warehouse = mapper.createEntity(warehouseDto);
        return warehouseRepository.save(warehouse);
    }
}
