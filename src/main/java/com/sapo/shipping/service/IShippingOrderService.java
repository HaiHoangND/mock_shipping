package com.sapo.shipping.service;

import com.sapo.shipping.dto.ShippingOrderDto;
import com.sapo.shipping.entity.ShippingOrder;
import org.springframework.data.domain.Page;

public interface IShippingOrderService {
    Page<ShippingOrder> getAll(int pageNumber, int pageSize);

    ShippingOrder getById(int id);

    ShippingOrder create(ShippingOrderDto shippingOrderDto);

    ShippingOrder update(int id, ShippingOrderDto shippingOrderDto);

    Boolean delete(int id);
}
