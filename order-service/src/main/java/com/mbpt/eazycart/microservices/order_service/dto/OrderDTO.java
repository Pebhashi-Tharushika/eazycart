package com.mbpt.eazycart.microservices.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private Double totalAmount;
    private Date orderDate;
    private String ownerName;
    private String address;
    private List<Long> productIds;
}
