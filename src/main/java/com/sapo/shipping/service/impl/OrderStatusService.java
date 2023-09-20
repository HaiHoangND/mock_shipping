package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.OrderStatusDto;
import com.sapo.shipping.entity.OrderStatus;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.OrderStatusMapper;
import com.sapo.shipping.repository.OrderStatusRepository;
import com.sapo.shipping.service.IOrderStatusService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderStatusService implements IOrderStatusService {

    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusMapper mapper;
    private final Validator validator;

    public OrderStatusService(OrderStatusRepository orderStatusRepository, OrderStatusMapper mapper, Validator validator) {
        this.orderStatusRepository = orderStatusRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public OrderStatus getById(int id) {
        return orderStatusRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Order not found"));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public int create(OrderStatusDto orderStatusDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(orderStatusDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        OrderStatus orderStatus = mapper.createEntity(orderStatusDto);
        orderStatusRepository.save(orderStatus);
        return orderStatus.getId();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderStatus update(int id, OrderStatusDto orderStatusDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(orderStatusDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        OrderStatus orderStatus = orderStatusRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Order not found"));
        mapper.updateEntity(orderStatus, orderStatusDto);
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public Boolean delete(int id) {
        orderStatusRepository.findById(id)
                .orElseThrow(()-> new BusinessException("404", "error", "Order not found"));
        orderStatusRepository.deleteById(id);
        return true;
    }
}
