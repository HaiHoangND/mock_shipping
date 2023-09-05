package com.sapo.shipping.service;

import com.sapo.shipping.dto.OrderRouteDto;
import com.sapo.shipping.entity.OrderRoute;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderRouteService {
    List<OrderRoute> getAll();

    OrderRoute getById(int id);

    OrderRoute create(OrderRouteDto orderRouteDto);

    OrderRoute update(int id, OrderRouteDto orderRouteDto);

    List<OrderRoute> delete(int id);

    OrderRoute getRouteByOrderIdAndRouteId(Integer orderId, Integer routeId);
}
