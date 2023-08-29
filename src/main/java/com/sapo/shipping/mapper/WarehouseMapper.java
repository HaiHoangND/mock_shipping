package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.WarehouseDto;
import com.sapo.shipping.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WarehouseMapper extends GenericMapper<WarehouseDto,Warehouse> {

    WarehouseDto toDto(Warehouse entity);

    Warehouse createEntity(WarehouseDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Warehouse entity, WarehouseDto dto);
}
