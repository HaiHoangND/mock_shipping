package com.sapo.shipping.service.impl;

import com.sapo.shipping.repository.ShippingOrderRepository;
import com.sapo.shipping.service.IShippingOrderService;
import org.springframework.stereotype.Service;

@Service
public class ShippingOrderService implements IShippingOrderService {
    private final ShippingOrderRepository shippingOrderRepository;

    public ShippingOrderService(ShippingOrderRepository shippingOrderRepository) {
        this.shippingOrderRepository = shippingOrderRepository;
    }
}
