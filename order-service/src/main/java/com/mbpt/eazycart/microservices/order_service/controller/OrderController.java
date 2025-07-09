package com.mbpt.eazycart.microservices.order_service.controller;

import com.mbpt.eazycart.microservices.order_service.dto.OrderDTO;
import com.mbpt.eazycart.microservices.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        if (Objects.isNull(orderDTO))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        final OrderDTO dto = orderService.getFinalizeOrder(orderDTO);
        if (Objects.isNull(dto))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
        if (Objects.isNull(foundOrder))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(foundOrder);
    }

    @PutMapping
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO) {
        if (Objects.isNull(orderDTO) || Objects.isNull(orderDTO.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        final OrderDTO dto = orderService.getFinalizeOrder(orderDTO);
        if (Objects.isNull(dto))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        OrderDTO updatedOrder = orderService.updateOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable Integer id) {
        OrderDTO deletedOrder = orderService.deleteOrder(id);
        if (Objects.isNull(deletedOrder))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(deletedOrder);
    }
}
