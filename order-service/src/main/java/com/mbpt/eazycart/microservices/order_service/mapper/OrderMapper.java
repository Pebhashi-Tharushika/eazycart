package com.mbpt.eazycart.microservices.order_service.mapper;

import com.mbpt.eazycart.microservices.order_service.dto.OrderDTO;
import com.mbpt.eazycart.microservices.order_service.entity.OrderEntity;
import com.mbpt.eazycart.microservices.order_service.entity.ProductEntity;
import com.mbpt.eazycart.microservices.order_service.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected ProductRepository productRepository;

    // DTO to Entity mapping
    @Mapping(source = "id", target = "orderId")
    @Mapping(target = "products", expression = "java(getProducts(orderDTO.getProductIds()))")
    public abstract OrderEntity toEntity(OrderDTO orderDTO);

    // Entity to DTO mapping
    @Mapping(source = "orderId", target = "id")
    @Mapping(target = "productIds", expression = "java(getProductIds(orderEntity.getProducts()))")
    public abstract OrderDTO toDto(OrderEntity orderEntity);

    protected List<ProductEntity> getProducts(List<Integer> productIds) {
        if (productIds == null) return null;
        return productIds.stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Product not found: " + id)))
                .collect(Collectors.toList());
    }

    protected List<Integer> getProductIds(List<ProductEntity> products) {
        if (products == null) return null;
        return products.stream()
                .map(ProductEntity::getId)
                .collect(Collectors.toList());
    }
}

