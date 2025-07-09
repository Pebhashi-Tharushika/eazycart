package com.mbpt.eazycart.microservices.order_service.service;

import com.mbpt.eazycart.microservices.order_service.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDTO getFinalizeOrder(OrderDTO orderDTO);

    List<OrderDTO> findAllOrders();

    OrderDTO findOrderById(Integer id);

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(OrderDTO orderDTO);

    OrderDTO deleteOrder(Integer id);

}