package com.sapo.shipping.mapper;

import com.sapo.shipping.dto.ProductDto;
import com.sapo.shipping.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<ProductDto, Product> {
    @Override
    @Mapping(source = "shippingOrder.id", target = "shippingOrderId")
    ProductDto toDto(Product entity);

    @Override
    @Mapping(source = "shippingOrderId", target = "shippingOrder.id")
    Product createEntity(ProductDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "shippingOrderId", target = "shippingOrder.id")
    void updateEntity(@MappingTarget Product entity, ProductDto dto);
}
