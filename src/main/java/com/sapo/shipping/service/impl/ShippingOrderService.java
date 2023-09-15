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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
    public Page<ShippingOrder> getAll(int pageNumber, int pageSize, String orderCode) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        if (orderCode != null) {
            return shippingOrderRepository.findByOrderCode(orderCode, pageRequest);
        }
        return shippingOrderRepository.findAll(pageRequest);
    }

    @Override
    public ShippingOrder getById(int id) {
        return shippingOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Order not found"));
    }

    @Override
    public Page<ShippingOrder> getShippingOrderByShopOwner(Integer shopOwnerId,int pageNumber, int pageSize, String orderCode){
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return shippingOrderRepository.getShippingOrderByShopOwner(shopOwnerId, pageRequest, orderCode);
    };

    @Override
    public Double getTotalRevenueByShopOwnerId(Integer shopOwnerId){
        List<ShippingOrder> shippingOrders = shippingOrderRepository.getAccountedShippingOrdersByShopOwnerId(shopOwnerId);
        Double revenue = 0.0;
        for(ShippingOrder shippingOrder : shippingOrders){
            List<Product> products = shippingOrder.getProducts();
            for(Product product: products){
                revenue += product.getPrice() * product.getQuantity();
            }
        }
        return revenue;
    };

    @Override
    public ShippingOrder findByCode(String orderCode) {
        return shippingOrderRepository.findByCode(orderCode);
    }

    @Override
    public List<MonthProfit> statisticRevenueOfYear(Integer month, Integer year){
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        List<MonthProfit> monthProfits = new ArrayList<>();

        for(int day = 1; day <= daysInMonth; day++){
            MonthProfit monthProfit = new MonthProfit();
            Double profit = shippingOrderRepository.getTotalRevenueForDay(day, month, year);
            monthProfit.setDate(day);
            monthProfit.setProfit(profit == null ? 0.0 : profit);
            monthProfits.add(monthProfit);
        }
        return monthProfits;
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
        int availableShippers = userRepository.getAllAvailableShippers().size();
        Map<String, Object> statisticsMap = new HashMap<>();
        statisticsMap.put("ShippingOrders", shippingOrders);
        statisticsMap.put("Delivering", deliveringShippingOrders);
        statisticsMap.put("Shippers", availableShippers);
        data.add(statisticsMap);
        return data;
    };

    @Override
    public List<Object> shopOwnerStatistic(Integer shopOwnerId){
        PageRequest p = PageRequest.of(0, Integer.MAX_VALUE);
        List<Object> data = new ArrayList<>();
        int shippingOrders = shippingOrderRepository.getShippingOrderByShopOwner(shopOwnerId,p, null).getNumberOfElements();
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
    @Transactional(rollbackOn = Exception.class)
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
    @Transactional(rollbackOn = Exception.class)
    public Boolean delete(int id) {
        shippingOrderRepository.findById(id)
                .orElseThrow(()-> new BusinessException("404", "error", "Order not found"));
        shippingOrderRepository.deleteById(id);
        return true;
    }
}
