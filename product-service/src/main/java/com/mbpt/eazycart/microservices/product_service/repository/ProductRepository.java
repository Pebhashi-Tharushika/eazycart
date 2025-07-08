package com.mbpt.eazycart.microservices.product_service.repository;

import com.mbpt.eazycart.microservices.product_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}
