package com.sapo.shipping.service;

import com.sapo.shipping.dto.ShippingOrderDto;
import com.sapo.shipping.dto.MonthProfit;
import com.sapo.shipping.dto.ShippingOrderWithStatus;
import com.sapo.shipping.entity.ShippingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IShippingOrderService {
    Page<ShippingOrder> getAll(int pageNumber, int pageSize, String orderCode);

    ShippingOrder getById(int id);

    int create(ShippingOrderDto shippingOrderDto);

    int update(int id, ShippingOrderDto shippingOrderDto);

    Boolean delete(int id);

    ShippingOrder findByCode(String orderCode);

    Long countShippingOrdersAreDelivering();

    Long countSuccessfulDeliveredShippingOrders();

    Double getTotalRevenueForDay(Integer day, Integer month, Integer year);

    List<MonthProfit> statisticRevenueOfYear(Integer month, Integer year);

    List<MonthProfit> statisticRevenueOfMonth(Integer month, Integer year, Integer shopOwnerId);

    List<Object> coordinatorStatistic();

    Page<ShippingOrder> getShippingOrderByShopOwner(Integer shopOwnerId, int pageNumber, int pageSize, String orderCode);

    Double getTotalRevenueByShopOwnerId(Integer shopOwnerId);

    List<Object> shopOwnerStatistic(Integer shopOwnerId);

    List<ShippingOrderWithStatus> pieChartStatistic(int day, int month, int year);



}
