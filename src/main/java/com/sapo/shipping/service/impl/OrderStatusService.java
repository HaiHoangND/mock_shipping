package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.OrderStatusDto;
import com.sapo.shipping.entity.OrderStatus;
import com.sapo.shipping.repository.OrderStatusRepository;
import com.sapo.shipping.service.IOrderStatusService;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService implements IOrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public OrderStatus getById(int id) {
        return null;
    }

    @Override
    public OrderStatus create(OrderStatusDto orderStatusDto) {
        return null;
    }

    @Override
    public OrderStatus update(int id, OrderStatusDto orderStatusDto) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }
}
