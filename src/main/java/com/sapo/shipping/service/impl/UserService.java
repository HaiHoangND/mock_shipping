package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.UserDto;
import com.sapo.shipping.dto.UserWithStatus;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.UserMapper;
import com.sapo.shipping.repository.UserRepository;
import com.sapo.shipping.service.IUserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final Validator validator;
    private final UserMapper mapper;


    public UserService( UserRepository userRepository, Validator validator, UserMapper mapper) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public Page<User> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return userRepository.findAll(pageRequest);
    }

    @Override
    public Page<UserWithStatus> getAllShippers(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<User> shippersPage = userRepository.getAllShippers(pageRequest);

        List<UserWithStatus> shippersWithStatus = new ArrayList<>();
        for (User user : shippersPage.getContent()) {
            UserWithStatus userWithStatus = new UserWithStatus(user);
            Integer ordersInProgress = userRepository.getFilteredShippingOrders(user.getId(), "unSuccessful").size();
            userWithStatus.setOrdersInProgress(ordersInProgress);
            shippersWithStatus.add(userWithStatus);
        }

        return new PageImpl<>(shippersWithStatus, pageRequest, shippersPage.getTotalElements());
    }

    @Override
    public List<ShippingOrder> getFilteredShippingOrders(Integer shipperId,String statusFilter){
        return userRepository.getFilteredShippingOrders(shipperId,statusFilter);
    }

    @Override
    public List<UserWithStatus> getShippersWithStatus(){
        List<User> shippers = userRepository.getAllAvailableShippers();
        List<UserWithStatus> usersWithStatus = new ArrayList<>();
        for (User user : shippers) {
            UserWithStatus userWithStatus = new UserWithStatus(user);
            Integer ordersInProgress = userRepository.getFilteredShippingOrders(user.getId(),"unSuccessful").size();
            userWithStatus.setOrdersInProgress(ordersInProgress);

            usersWithStatus.add(userWithStatus);
        }
        return usersWithStatus;
    };

    @Override
    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "User not found"));
    }

    @Override
    public User create(UserDto userDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(userDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        User user = mapper.createEntity(userDto);
        return userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User update(int id, UserDto userDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(userDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400", "error", errors.get(0));
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Order not found"));
        mapper.updateEntity(user, userDto);
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(int id) {
        userRepository.findById(id)
                .orElseThrow(()-> new BusinessException("404", "error", "Order not found"));
        userRepository.deleteById(id);
        return true;
    }
}
