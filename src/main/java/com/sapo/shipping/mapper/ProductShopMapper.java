package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.dto.ProductShopDto;
import com.sapo.shipping.entity.Product;
import com.sapo.shipping.entity.ProductShop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductShopMapper extends GenericMapper<ProductShopDto, ProductShop>{
    @Override
    @Mapping(source = "shopOwner.id", target = "shopOwnerId")
    ProductShopDto toDto(ProductShop entity);

    @Override
    @Mapping(source = "shopOwnerId", target = "shopOwner.id")
    ProductShop createEntity(ProductShopDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "shopOwnerId", target = "shopOwner.id")
    void updateEntity(@MappingTarget ProductShop entity, ProductShopDto dto);
}
