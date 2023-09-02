package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.ShippingOrderDto;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.ShippingOrderMapper;
import com.sapo.shipping.repository.ShippingOrderRepository;
import com.sapo.shipping.service.IShippingOrderService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingOrderService implements IShippingOrderService {
    private final ShippingOrderRepository shippingOrderRepository;
    private final Validator validator;
    private final ShippingOrderMapper mapper;

    public ShippingOrderService(ShippingOrderRepository shippingOrderRepository, Validator validator, ShippingOrderMapper mapper) {
        this.shippingOrderRepository = shippingOrderRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public Page<ShippingOrder> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return shippingOrderRepository.findAll(pageRequest);
    }

    @Override
    public ShippingOrder getById(int id) {
        return shippingOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Order not found"));
    }

    @Override
    public ShippingOrder create(ShippingOrderDto shippingOrderDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(shippingOrderDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        ShippingOrder shippingOrder = mapper.createEntity(shippingOrderDto);
        return shippingOrderRepository.save(shippingOrder);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ShippingOrder update(int id, ShippingOrderDto shippingOrderDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(shippingOrderDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        ShippingOrder shippingOrder = shippingOrderRepository.findById(id)
                .orElseThrow(()-> new BusinessException("404", "error", "Order not found"));
        mapper.updateEntity(shippingOrder, shippingOrderDto);
        return shippingOrderRepository.save(shippingOrder);
    }

    @Override
    public Boolean delete(int id) {
        shippingOrderRepository.findById(id)
                .orElseThrow(()-> new BusinessException("404", "error", "Order not found"));
        shippingOrderRepository.deleteById(id);
        return true;
    }
}
