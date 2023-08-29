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
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    ShippingOrderDto toDto(ShippingOrder entity);

    @Override
    @Mapping(source = "receiverId", target = "receiver.id")
    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "warehouseId", target = "warehouse.id")
    ShippingOrder createEntity(ShippingOrderDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "receiverId", target = "receiver.id")
    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "warehouseId", target = "warehouse.id")
    void updateEntity(@MappingTarget ShippingOrder entity, ShippingOrderDto dto);
}
