package com.mbpt.eazycart.microservices.order_service.mapper;

import com.mbpt.eazycart.microservices.order_service.dto.ProductDTO;
import com.mbpt.eazycart.microservices.order_service.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(ProductEntity productEntity);

    ProductEntity toEntity(ProductDTO productDTO);
}

