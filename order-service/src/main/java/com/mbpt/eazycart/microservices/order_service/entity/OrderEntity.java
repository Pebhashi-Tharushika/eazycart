package com.mbpt.eazycart.microservices.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "address")
    private String address;

}
