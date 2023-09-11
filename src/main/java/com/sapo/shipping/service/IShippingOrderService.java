package com.sapo.shipping.service;

import com.sapo.shipping.dto.ShippingOrderDto;
import com.sapo.shipping.dto.MonthProfit;
import com.sapo.shipping.entity.ShippingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IShippingOrderService {
    Page<ShippingOrder> getAll(int pageNumber, int pageSize);

    ShippingOrder getById(int id);

    ShippingOrder create(ShippingOrderDto shippingOrderDto);

    ShippingOrder update(int id, ShippingOrderDto shippingOrderDto);

    Boolean delete(int id);

    ShippingOrder findByOrderCode(String orderCode);

    Long countShippingOrdersAreDelivering();

    Long countSuccessfulDeliveredShippingOrders();

    Double getTotalRevenueForDay(Integer day, Integer month, Integer year);

    List<MonthProfit> statisticRevenueOfYear(Integer year);

    List<Object> coordinatorStatistic();

    List<ShippingOrder> getShippingOrderByShopOwner(Integer shopOwnerId);

}
