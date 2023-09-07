package com.sapo.shipping.service.impl;

import com.sapo.shipping.auth.permission.Role;
import com.sapo.shipping.dto.UserDto;
import com.sapo.shipping.dto.WarehousesStatistic;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import com.sapo.shipping.entity.Warehouse;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.UserMapper;
import com.sapo.shipping.repository.UserRepository;
import com.sapo.shipping.repository.WarehouseRepository;
import com.sapo.shipping.service.IUserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final Validator validator;
    private final UserMapper mapper;

    private final WarehouseRepository warehouseRepository;

    public UserService(WarehouseRepository warehouseRepository, UserRepository userRepository, Validator validator, UserMapper mapper) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.mapper = mapper;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public Page<User> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(pageRequest);
    }

    @Override
    public List<ShippingOrder> getFilteredShippingOrders(Integer shipperId,String statusFilter){
        return userRepository.getFilteredShippingOrders(shipperId,statusFilter);
    }

    @Override
    public List<WarehousesStatistic> statisticAllWarehouses(){
        List<Warehouse> warehouses = warehouseRepository.findAll();
        List<WarehousesStatistic> warehousesStatistics = new ArrayList<>();
        for(Warehouse warehouse: warehouses){
            WarehousesStatistic warehousesStatistic = new WarehousesStatistic(warehouse);
            Long delivering = warehouseRepository.countShippingOrdersBeingDelivered(warehouse.getId());
            int shippers = warehouseRepository.getAllUsersByWarehouseId(warehouse.getId(), Role.SHIPPER).size();
            int shippingOrders = warehouseRepository.getAllShippingOrdersByWarehouseId(warehouse.getId()).size();
            warehousesStatistic.setDelivering(delivering);
            warehousesStatistic.setShippers(shippers);
            warehousesStatistic.setShippingOrders(shippingOrders);
            warehousesStatistics.add(warehousesStatistic);
        }
        return warehousesStatistics;
    };

    @Override
    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "User not found"));
    }

    @Override
    public User create(UserDto userDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(userDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        User user = mapper.createEntity(userDto);
        return userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User update(int id, UserDto userDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(userDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Order not found"));
        mapper.updateEntity(user, userDto);
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(int id) {
        userRepository.findById(id)
                .orElseThrow(()-> new BusinessException("404", "error", "Order not found"));
        userRepository.deleteById(id);
        return true;
    }
}
