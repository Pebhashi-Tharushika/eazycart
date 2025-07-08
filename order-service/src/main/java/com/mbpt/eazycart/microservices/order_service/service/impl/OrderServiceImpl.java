package com.mbpt.eazycart.microservices.order_service.service.impl;

import com.mbpt.eazycart.microservices.order_service.dto.OrderDTO;
import com.mbpt.eazycart.microservices.order_service.entity.OrderEntity;
import com.mbpt.eazycart.microservices.order_service.mapper.OrderMapper;
import com.mbpt.eazycart.microservices.order_service.repository.OrderRepository;
import com.mbpt.eazycart.microservices.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<OrderDTO> findAllOrders() {
        List<OrderEntity> allOrderEntities = orderRepository.findAll();
        return allOrderEntities.stream().map(entity -> orderMapper.toDto(entity)).collect(Collectors.toList());
    }

    @Override
    public OrderDTO findOrderById(Integer id) {
        OrderEntity foundUserEntity = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("order not found"));
        return orderMapper.toDto(foundUserEntity);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        return orderMapper.toDto(savedOrderEntity);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        OrderEntity foundOrderEntity = orderRepository.findById(orderDTO.getId()).orElseThrow(() -> new RuntimeException("order not found"));
        foundOrderEntity.setOrderDate(orderEntity.getOrderDate());
        foundOrderEntity.setOwnerName(orderEntity.getOwnerName());
        foundOrderEntity.setAddress(orderEntity.getAddress());
        foundOrderEntity.setProducts(orderEntity.getProducts());
        OrderEntity updatedOrderEntity = orderRepository.save(foundOrderEntity);
        return orderMapper.toDto(updatedOrderEntity);
    }

    @Override
    public OrderDTO deleteOrder(Integer id) {
        OrderEntity foundOrderEntity = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("order not found"));
        OrderDTO deletedOrder = orderMapper.toDto(foundOrderEntity);
        orderRepository.deleteById(id);
        return deletedOrder;
    }
}
