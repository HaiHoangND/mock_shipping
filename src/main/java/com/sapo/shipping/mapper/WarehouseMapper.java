package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.WarehouseDto;
import com.sapo.shipping.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarehouseMapper extends GenericMapper<WarehouseDto,Warehouse> {

    @Override
    WarehouseDto toDto(Warehouse entity);

    @Override
    Warehouse createEntity(WarehouseDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntity(Warehouse entity, WarehouseDto dto);
}
