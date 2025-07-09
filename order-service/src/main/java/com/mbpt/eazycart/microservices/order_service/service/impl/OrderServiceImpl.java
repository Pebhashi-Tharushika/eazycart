package com.mbpt.eazycart.microservices.order_service.service.impl;

import com.mbpt.eazycart.microservices.order_service.dto.OrderDTO;
import com.mbpt.eazycart.microservices.order_service.dto.ProductDTO;
import com.mbpt.eazycart.microservices.order_service.entity.OrderEntity;
import com.mbpt.eazycart.microservices.order_service.entity.ProductEntity;
import com.mbpt.eazycart.microservices.order_service.exception.InvalidOrderItemsException;
import com.mbpt.eazycart.microservices.order_service.exception.OrderNotFoundException;
import com.mbpt.eazycart.microservices.order_service.mapper.OrderMapper;
import com.mbpt.eazycart.microservices.order_service.mapper.ProductMapper;
import com.mbpt.eazycart.microservices.order_service.repository.OrderRepository;
import com.mbpt.eazycart.microservices.order_service.service.OrderService;
import com.mbpt.eazycart.microservices.order_service.util.InterServiceCommunicationHandler;
import com.mbpt.eazycart.microservices.order_service.util.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductClient productClient;


    @Override
    public OrderDTO getFinalizeOrder(OrderDTO orderDTO) {
        List<ProductDTO> productDTOList = productClient.getAllProducts();
        if (isAllOrderItemExists(orderDTO, productDTOList)) {
            return orderDTO;
        }
        return null;
    }

    private boolean isAllOrderItemExists(OrderDTO orderDTO, List<ProductDTO> productDTOList) {
        List<Integer> productIds = orderDTO.getProductIds();
        Set<Integer> availableProductIds = productDTOList.stream()
                .map(ProductDTO::getId)
                .collect(Collectors.toSet());
        return availableProductIds.containsAll(productIds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> findAllOrders() {
        List<OrderEntity> allOrderEntities = orderRepository.findAll();
        List<OrderDTO> allOrderDTOs = allOrderEntities.stream()
                .map(orderEntity -> {
                    OrderDTO dto = orderMapper.toDto(orderEntity);
                    List<ProductEntity> products = orderEntity.getProducts();
                    List<Integer> productIds = products.stream().map(ProductEntity::getId).toList();
                    dto.setProductIds(productIds);
                    return dto;
                }).collect(Collectors.toList());
        return allOrderDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Integer id) {
        OrderEntity foundOrderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        OrderDTO foundOrderDTO = orderMapper.toDto(foundOrderEntity);
        List<Integer> productIds = foundOrderEntity.getProducts().stream().map(ProductEntity::getId).toList();
        foundOrderDTO.setProductIds(productIds);
        return foundOrderDTO;
    }

    @Override
    @Transactional // Add @Transactional for write operations
    public OrderDTO createOrder(OrderDTO orderDTO) {

        List<ProductDTO> productDTOList = productClient.getAllProducts();

        if (!isAllOrderItemExists(orderDTO, productDTOList)) {
            throw new InvalidOrderItemsException("One or more product IDs in the order do not exist.");
        }

        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        List<ProductEntity> productEntities = orderDTO.getProductIds().stream().map(id -> {
            ProductDTO productDTO = productClient.findProductById(id);
            return productMapper.toEntity(productDTO);
        }).collect(Collectors.toCollection(ArrayList::new));
        orderEntity.setProducts(productEntities);

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        OrderDTO savedProductDTO = orderMapper.toDto(savedOrderEntity);
        List<Integer> productIds = savedOrderEntity.getProducts().stream().map(ProductEntity::getId).toList();
        savedProductDTO.setProductIds(productIds);

        return savedProductDTO;
    }


    @Override
    @Transactional
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        OrderEntity foundOrderEntity = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderDTO.getId()));

        List<ProductDTO> productDTOList = productClient.getAllProducts();

        if (!isAllOrderItemExists(orderDTO, productDTOList)) {
            throw new InvalidOrderItemsException("One or more product IDs in the updated order do not exist.");
        }

        OrderEntity updatedOrderEntity = orderMapper.toEntity(orderDTO);
        List<ProductEntity> productEntities = orderDTO.getProductIds().stream().map(id -> {
            ProductDTO productDTO = productClient.findProductById(id);
            return productMapper.toEntity(productDTO);
        }).collect(Collectors.toCollection(ArrayList::new));
        updatedOrderEntity.setProducts(productEntities);

        foundOrderEntity.setOrderDate(updatedOrderEntity.getOrderDate());
        foundOrderEntity.setOwnerName(updatedOrderEntity.getOwnerName());
        foundOrderEntity.setAddress(updatedOrderEntity.getAddress());
        foundOrderEntity.setProducts(updatedOrderEntity.getProducts());
        OrderEntity savedOrderEntity = orderRepository.save(foundOrderEntity);

        OrderDTO savedProductDTO = orderMapper.toDto(savedOrderEntity);
        List<Integer> productIds = savedOrderEntity.getProducts().stream().map(ProductEntity::getId).toList();
        savedProductDTO.setProductIds(productIds);

        return savedProductDTO;
    }

    @Override
    @Transactional
    public OrderDTO deleteOrder(Integer id) {
        OrderEntity foundOrderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        OrderDTO deletedOrderDTO = orderMapper.toDto(foundOrderEntity);
        List<Integer> productIds = foundOrderEntity.getProducts().stream().map(ProductEntity::getId).toList();
        deletedOrderDTO.setProductIds(productIds);

        orderRepository.deleteById(id);
        return deletedOrderDTO;
    }
}
