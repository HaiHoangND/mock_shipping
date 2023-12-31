package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.OrderRouteDto;
import com.sapo.shipping.entity.OrderRoute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderRouteMapper extends GenericMapper<OrderRouteDto, OrderRoute>{
    @Override
    @Mapping(source = "shippingOrder.id", target = "shippingOrderId")
    OrderRouteDto toDto(OrderRoute entity);

    @Override
    @Mapping(source = "shippingOrderId", target = "shippingOrder.id")
    OrderRoute createEntity(OrderRouteDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "shippingOrderId", target = "shippingOrder.id")
    void updateEntity(@MappingTarget OrderRoute entity, OrderRouteDto dto);
}
