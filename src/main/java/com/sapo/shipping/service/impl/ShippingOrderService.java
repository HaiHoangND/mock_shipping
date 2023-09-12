package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.ShippingOrderDto;
import com.sapo.shipping.dto.MonthProfit;
import com.sapo.shipping.entity.Product;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.ShippingOrderMapper;
import com.sapo.shipping.repository.ShippingOrderRepository;
import com.sapo.shipping.repository.UserRepository;
import com.sapo.shipping.service.IShippingOrderService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShippingOrderService implements IShippingOrderService {
    private final ShippingOrderRepository shippingOrderRepository;
    private final Validator validator;
    private final ShippingOrderMapper mapper;

    private final UserRepository userRepository;

    public ShippingOrderService(UserRepository userRepository, ShippingOrderRepository shippingOrderRepository, Validator validator, ShippingOrderMapper mapper) {
        this.shippingOrderRepository = shippingOrderRepository;
        this.validator = validator;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public Page<ShippingOrder> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return shippingOrderRepository.findAll(pageRequest);
    }

    @Override
    public ShippingOrder getById(int id) {
        return shippingOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Order not found"));
    }

    @Override
    public List<ShippingOrder> getShippingOrderByShopOwner(Integer shopOwnerId){
        return shippingOrderRepository.getShippingOrderByShopOwner(shopOwnerId);
    };

    @Override
    public Double getTotalRevenueByShopOwnerId(Integer shopOwnerId){
        List<ShippingOrder> shippingOrders = shippingOrderRepository.getAccountedShippingOrdersByShopOwnerId(shopOwnerId);
        Double revenue = 0.0;
        for(ShippingOrder shippingOrder : shippingOrders){
            List<Product> products = shippingOrder.getProducts();
            for(Product product: products){
                revenue += product.getPrice();
            }
        }
        return revenue;
    };

    @Override
    public ShippingOrder findByOrderCode(String orderCode) {
        return shippingOrderRepository.findByOrderCode(orderCode);
    }

    @Override
    public List<MonthProfit> statisticRevenueOfYear(Integer year){
        return shippingOrderRepository.statisticRevenueOfYear(year);
    };

    @Override
    public Long countShippingOrdersAreDelivering(){
        return shippingOrderRepository.countShippingOrdersAreDelivering(null);
    };

    @Override
    public Long countSuccessfulDeliveredShippingOrders(){
        return shippingOrderRepository.countSuccessfulDeliveredShippingOrders();
    };

    @Override
    public Double getTotalRevenueForDay(Integer day, Integer month, Integer year){
        return shippingOrderRepository.getTotalRevenueForDay(day, month, year);
    };

    @Override
    public List<Object> coordinatorStatistic(){
        List<Object> data = new ArrayList<>();
        int shippingOrders = shippingOrderRepository.findAll().size();
        Long deliveringShippingOrders = shippingOrderRepository.countShippingOrdersAreDelivering(null);
        int availableShippers = userRepository.getAllShippers().size();
        Map<String, Object> statisticsMap = new HashMap<>();
        statisticsMap.put("ShippingOrders", shippingOrders);
        statisticsMap.put("Delivering", deliveringShippingOrders);
        statisticsMap.put("Shippers", availableShippers);
        data.add(statisticsMap);
        return data;
    };

    @Override
    public List<Object> shopOwnerStatistic(Integer shopOwnerId){
        List<Object> data = new ArrayList<>();
        int shippingOrders = shippingOrderRepository.getShippingOrderByShopOwner(shopOwnerId).size();
        Long deliveringShippingOrders = shippingOrderRepository.countShippingOrdersAreDelivering(shopOwnerId);
        int successfulShippingOrders = shippingOrderRepository.getAccountedShippingOrdersByShopOwnerId(shopOwnerId).size();
        Map<String, Object> statisticsMap = new HashMap<>();
        statisticsMap.put("ShippingOrders", shippingOrders);
        statisticsMap.put("Delivering", deliveringShippingOrders);
        statisticsMap.put("Successful", successfulShippingOrders);
        data.add(statisticsMap);
        return data;
    };

    @Override
    public ShippingOrder create(ShippingOrderDto shippingOrderDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(shippingOrderDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        ShippingOrder shippingOrder = mapper.createEntity(shippingOrderDto);
        return shippingOrderRepository.save(shippingOrder);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ShippingOrder update(int id, ShippingOrderDto shippingOrderDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(shippingOrderDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        ShippingOrder shippingOrder = shippingOrderRepository.findById(id)
                .orElseThrow(()-> new BusinessException("404", "error", "Order not found"));
        mapper.updateEntity(shippingOrder, shippingOrderDto);
        return shippingOrderRepository.save(shippingOrder);
    }

    @Override
    public Boolean delete(int id) {
        shippingOrderRepository.findById(id)
                .orElseThrow(()-> new BusinessException("404", "error", "Order not found"));
        shippingOrderRepository.deleteById(id);
        return true;
    }
}
