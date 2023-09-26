package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.ShopOwnerWithNumberOfShippingOrder;
import com.sapo.shipping.dto.UserDto;
import com.sapo.shipping.dto.UserWithStatus;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.UserMapper;
import com.sapo.shipping.repository.ShippingOrderRepository;
import com.sapo.shipping.repository.UserRepository;
import com.sapo.shipping.service.IUserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    private final ShippingOrderRepository shippingOrderRepository;
    private final Validator validator;
    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;
    public UserService( ShippingOrderRepository shippingOrderRepository, UserRepository userRepository, Validator validator, UserMapper mapper,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.shippingOrderRepository = shippingOrderRepository;
    }

    @Override
    public Page<User> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return userRepository.findAll(pageRequest);
    }

    @Override
    public Page<User> getAllShopOwners(int pageNumber, int pageSize, String keyWord) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return userRepository.getAllShopOwners(pageRequest, keyWord);
    }

    @Override
    public Page<UserWithStatus> getAllShippers(int pageNumber, int pageSize, String keyWord) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<User> shippersPage = userRepository.getAllShippers(pageRequest, keyWord);

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
    }

    @Override
    public List<ShopOwnerWithNumberOfShippingOrder> getTop7ShopOwnerHaveMostShippingOrder(){
        List<User> shopOwners = userRepository.getAllShopOwnerNoPage();
        PageRequest p = PageRequest.of(0, Integer.MAX_VALUE);
        List<ShopOwnerWithNumberOfShippingOrder> shopOwnerWithNumberOfShippingOrderList = new ArrayList<>();
        for(User shopOwner : shopOwners){
            ShopOwnerWithNumberOfShippingOrder shopOwnerWithNumberOfShippingOrder = new ShopOwnerWithNumberOfShippingOrder(shopOwner);
            int numberOfShippingOrders = shippingOrderRepository.getAccountedShippingOrdersByShopOwnerId(shopOwner.getId()).size();
            shopOwnerWithNumberOfShippingOrder.setNumberOfShippingOrders(numberOfShippingOrders);
            if(shopOwnerWithNumberOfShippingOrder.getNumberOfShippingOrders() != 0){
                shopOwnerWithNumberOfShippingOrderList.add(shopOwnerWithNumberOfShippingOrder);
            }
        }
        // Sắp xếp danh sách theo numberOfShippingOrders giảm dần
        shopOwnerWithNumberOfShippingOrderList.sort(Comparator.comparingInt(ShopOwnerWithNumberOfShippingOrder::getNumberOfShippingOrders).reversed());

        // Lấy ra top 7 phần tử
        List<ShopOwnerWithNumberOfShippingOrder> top7ShopOwners = shopOwnerWithNumberOfShippingOrderList.stream()
                .limit(7)
                .collect(Collectors.toList());

        return top7ShopOwners;
    }

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
    public List<User> getAllShopOwnerNoPage(){
        return userRepository.getAllShopOwnerNoPage();
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
                .orElseThrow(() -> new BusinessException("404", "error", "User not found"));
        Optional<User> checkEmailUser = userRepository.findByEmail(userDto.getEmail());
        User checkPhoneUser = userRepository.findByPhone(userDto.getPhone());
        if (checkEmailUser.isPresent() && checkEmailUser.get().getId() != id){
            throw new BusinessException("400", "error", "Email đã tồn tại");
        }
        if (checkPhoneUser != null && checkPhoneUser.getId() != id){
            throw new BusinessException("400", "error", "Số điện thoại đã tồn tại");
        }
        mapper.updateEntity(user, userDto);
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(int id, String password){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "User not found"));
        if(password != null){
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(int id) {
        userRepository.findById(id)
                .orElseThrow(()-> new BusinessException("404", "error", "User not found"));
        userRepository.deleteById(id);
        return true;
    }
}
