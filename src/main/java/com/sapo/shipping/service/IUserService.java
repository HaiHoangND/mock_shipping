package com.sapo.shipping.service;

import com.sapo.shipping.dto.UserDto;
import com.sapo.shipping.dto.UserWithStatus;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUserService {
    Page<User> getAll(int pageNumber, int pageSize);

    User getById(int id);



    User create(UserDto userDto);

    User update(int id, UserDto userDto);

    Boolean delete(int id);

    List<ShippingOrder> getFilteredShippingOrders(Integer shipperId,String statusFilter);

    List<UserWithStatus> getShippersWithStatus();

    Page<UserWithStatus> getAllShippers(int pageNumber, int pageSize, String keyWord);

    Page<User> getAllShopOwners(int pageNumber, int pageSize, String keyWord);

    List<User> getAllShopOwnerNoPage();

}
