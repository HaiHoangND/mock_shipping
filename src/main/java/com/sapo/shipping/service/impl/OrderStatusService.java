package com.sapo.shipping.service.impl;

import com.sapo.shipping.repository.OrderStatusRepository;
import com.sapo.shipping.service.IOrderStatusService;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService implements IOrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }
}
