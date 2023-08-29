package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.UserDto;
import com.sapo.shipping.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDto, User> {
    @Override
    UserDto toDto(User entity);

    @Override
    User createEntity(UserDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget User entity, UserDto dto);
}
