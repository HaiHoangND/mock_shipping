package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.UserWithStatus;
import com.sapo.shipping.dto.WarehouseDto;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import com.sapo.shipping.entity.Warehouse;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.WarehouseMapper;
import com.sapo.shipping.repository.WarehouseRepository;
import com.sapo.shipping.service.IWarehouseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService implements IWarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper mapper;
    private final Validator validator;

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseMapper mapper, Validator validator) {
        this.warehouseRepository = warehouseRepository;
        this.mapper = mapper;
        this.validator = validator;
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
    public List<ShippingOrder> getAllShippingOrdersByWarehouseId(Integer warehouseId){
        return warehouseRepository.getAllShippingOrdersByWarehouseId(warehouseId);
    };

    @Override
    public List<User> getAllUsersByWarehouseId(Integer warehouseId, String role){
        return warehouseRepository.getAllUsersByWarehouseId(warehouseId, role);
    };

    @Override
    public List<User> findAvailableShippersByWarehouseId(Integer warehouseId){
        return warehouseRepository.findAvailableShippersByWarehouseId(warehouseId);
    };

    @Override
    public List<UserWithStatus> getShippersWithStatus(Integer warehouseId){
        List<User> availableShippers = warehouseRepository.findAvailableShippersByWarehouseId(warehouseId);
        List<User> allUsers = warehouseRepository.getAllUsersByWarehouseId(warehouseId, "SHIPPER");

        List<UserWithStatus> usersWithStatus = new ArrayList<>();

        for (User user : allUsers) {
            UserWithStatus userWithStatus = new UserWithStatus(user);

            if (availableShippers.contains(user)) {
                userWithStatus.setStatus("Đang rảnh");
            } else {
                userWithStatus.setStatus("Đang lấy hàng");
            }

            usersWithStatus.add(userWithStatus);
        }

        return usersWithStatus;
    };

    @Override
    public Long countShippingOrdersBeingDelivered(Integer warehouseId){
        return warehouseRepository.countShippingOrdersBeingDelivered(warehouseId);
    };

    @Override
    public Warehouse create(WarehouseDto warehouseDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(warehouseDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        Warehouse warehouse = mapper.createEntity(warehouseDto);
        return warehouseRepository.save(warehouse);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<Warehouse> delete(int id) {
        warehouseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Warehouse not found"));
        warehouseRepository.deleteById(id);
        return warehouseRepository.findAll();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Warehouse update(int id, WarehouseDto warehouseDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(warehouseDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Warehouse not found"));
        mapper.updateEntity(warehouse, warehouseDto);
        return warehouseRepository.save(warehouse);

    }
}
