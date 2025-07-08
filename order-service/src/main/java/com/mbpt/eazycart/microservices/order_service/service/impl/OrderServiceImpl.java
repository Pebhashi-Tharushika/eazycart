package com.mbpt.eazycart.microservices.order_service.service.impl;

import com.mbpt.eazycart.microservices.order_service.dto.OrderDTO;
import com.mbpt.eazycart.microservices.order_service.entity.OrderEntity;
import com.mbpt.eazycart.microservices.order_service.repository.OrderRepository;
import com.mbpt.eazycart.microservices.order_service.service.OrderService;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderDTO> findAllOrders() {
        List<OrderEntity> allOrderEntities = orderRepository.findAll();
        return List.of();
    }

    @Override
    public OrderDTO findOrderById(Integer id) {
        return null;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO deleteOrder(Integer id) {
        return null;
    }
}
