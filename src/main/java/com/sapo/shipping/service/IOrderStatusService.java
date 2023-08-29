package com.sapo.shipping.service;

import com.sapo.shipping.dto.OrderStatusDto;
import com.sapo.shipping.entity.OrderStatus;

public interface IOrderStatusService {
    OrderStatus getById(int id);

    OrderStatus create(OrderStatusDto orderStatusDto);

    OrderStatus update(int id, OrderStatusDto orderStatusDto);

    Boolean delete(int id);
}
