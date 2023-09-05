package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.OrderRouteDto;
import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.entity.OrderRoute;
import com.sapo.shipping.entity.Product;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.OrderRouteMapper;
import com.sapo.shipping.repository.OrderRouteRepository;
import com.sapo.shipping.service.IOrderRouteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderRouteService implements IOrderRouteService {
    private final OrderRouteRepository orderRouteRepository;
    private final Validator validator;

    private final OrderRouteMapper mapper;

    public OrderRouteService(OrderRouteRepository orderRouteRepository, Validator validator, OrderRouteMapper mapper) {
        this.orderRouteRepository = orderRouteRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public List<OrderRoute> getAll(){return orderRouteRepository.findAll();}

    @Override
    public OrderRoute getRouteByOrderIdAndRouteId(Integer orderId, Integer routeId){
        return orderRouteRepository.getRouteByOrderIdAndRouteId(orderId, routeId);
    };

    @Override
    public OrderRoute getById(int id){
        return orderRouteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Order Route not found"));
    }

    @Override
    public OrderRoute create(OrderRouteDto orderRouteDto){
        List<String> errors = new ArrayList<>();
        validator.validate(orderRouteDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        OrderRoute orderRoute = mapper.createEntity(orderRouteDto);
        return orderRouteRepository.save(orderRoute);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderRoute update(int id, OrderRouteDto orderRouteDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(orderRouteDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        OrderRoute orderRoute = orderRouteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "OrderRoute not found"));
        mapper.updateEntity(orderRoute, orderRouteDto);
        return orderRouteRepository.save(orderRoute);
    }

    @Override
    public List<OrderRoute> delete(int id) {
        orderRouteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "OrderRoute not found"));
        orderRouteRepository.deleteById(id);
        return orderRouteRepository.findAll();
    }
}
