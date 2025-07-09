package com.mbpt.eazycart.microservices.order_service.mapper;

import com.mbpt.eazycart.microservices.order_service.dto.OrderDTO;
import com.mbpt.eazycart.microservices.order_service.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(target = "products", ignore = true) // skip conversion here
    OrderEntity toEntity(OrderDTO orderDTO);

    @Mapping(source = "orderId", target = "id")
    @Mapping(target = "productIds", ignore = true) // skip conversion here
    OrderDTO toDto(OrderEntity orderEntity);
}


//    protected List<ProductEntity> getProducts(List<Integer> productIds) {
//        if (productIds == null) return null;
//        return productIds.stream()
//                .map(productClient::findProductById)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }
//
//    protected List<Integer> getProductIds(List<ProductEntity> products) {
//        if (products == null) return null;
//        return products.stream()
//                .map(ProductEntity::getId)
//                .collect(Collectors.toList());
//    }


