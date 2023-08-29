package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.ReceiverDto;
import com.sapo.shipping.entity.Receiver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface ReceiverMapper extends GenericMapper<ReceiverDto, Receiver>{
    ReceiverDto toDto(Receiver entity);

    Receiver createEntity(ReceiverDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Receiver entity, ReceiverDto dto);
}
