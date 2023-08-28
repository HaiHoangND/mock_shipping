package com.sapo.shipping.service.impl;

import com.sapo.shipping.repository.OrderRouteRepository;
import com.sapo.shipping.service.IOrderRouteService;
import org.springframework.stereotype.Service;

@Service
public class OrderRouteService implements IOrderRouteService {
    private final OrderRouteRepository orderRouteRepository;

    public OrderRouteService(OrderRouteRepository orderRouteRepository) {
        this.orderRouteRepository = orderRouteRepository;
    }


}
