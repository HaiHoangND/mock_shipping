package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.OrderStatusDto;
import com.sapo.shipping.entity.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper extends GenericMapper<OrderStatusDto, OrderStatus> {
    @Override
    @Mapping(source = "shippingOrder.id", target = "shippingOrderId")
    @Mapping(source = "shipper.id", target = "shipperId")
    @Mapping(source = "orderRoute.id", target = "orderRouteId")
    OrderStatusDto toDto(OrderStatus entity);

    @Override
    @Mapping(source = "shippingOrderId", target = "shippingOrder.id")
    @Mapping(source = "shipperId", target = "shipper.id")
    @Mapping(source = "orderRouteId", target = "orderRoute.id")
    OrderStatus createEntity(OrderStatusDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget OrderStatus entity, OrderStatusDto dto);
}
