package com.mbpt.eazycart.microservices.order_service.repository;

import com.mbpt.eazycart.microservices.order_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
