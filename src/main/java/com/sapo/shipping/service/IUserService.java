package com.sapo.shipping.service;

import com.sapo.shipping.dto.UserDto;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserService {
    Page<User> getAll(int pageNumber, int pageSize);

    User getById(int id);

    User create(UserDto userDto);

    User update(int id, UserDto userDto);

    Boolean delete(int id);

    List<ShippingOrder> getFilteredShippingOrders(Integer shipperId,String statusFilter);
}
