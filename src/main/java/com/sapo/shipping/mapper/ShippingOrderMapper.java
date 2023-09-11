package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.ShippingOrderDto;
import com.sapo.shipping.entity.ShippingOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShippingOrderMapper extends GenericMapper<ShippingOrderDto, ShippingOrder> {
    @Override
    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "shopOwner.id", target = "shopOwnerId")
    ShippingOrderDto toDto(ShippingOrder entity);

    @Override
    @Mapping(source = "receiverId", target = "receiver.id")
    @Mapping(source = "shopOwnerId", target = "shopOwner.id")
    ShippingOrder createEntity(ShippingOrderDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "receiverId", target = "receiver.id")
    @Mapping(source = "shopOwnerId", target = "shopOwner.id")
    void updateEntity(@MappingTarget ShippingOrder entity, ShippingOrderDto dto);
}
