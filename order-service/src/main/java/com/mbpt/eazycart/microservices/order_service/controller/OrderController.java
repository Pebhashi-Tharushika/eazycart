package com.mbpt.eazycart.microservices.order_service.controller;

import com.mbpt.eazycart.microservices.order_service.dto.OrderDTO;
import com.mbpt.eazycart.microservices.order_service.service.OrderService;
import com.mbpt.eazycart.microservices.order_service.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> createOrder(@Validated(ValidationGroups.OnCreate.class) @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<OrderDTO>> findAllOrder() {
        List<OrderDTO> allOrders = orderService.findAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(allOrders);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Integer id) {
        OrderDTO foundOrder = orderService.findOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundOrder);
    }

    @PutMapping
    public ResponseEntity<OrderDTO> updateOrder(@Validated(ValidationGroups.OnUpdate.class) @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable Integer id) {
        OrderDTO deletedOrder = orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(deletedOrder);
    }

}