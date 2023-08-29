package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.SenderDto;
import com.sapo.shipping.entity.Sender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface SenderMapper extends GenericMapper<SenderDto, Sender>{
    SenderDto toDto(Sender entity);

    Sender createEntity(SenderDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Sender entity, SenderDto dto);

}
