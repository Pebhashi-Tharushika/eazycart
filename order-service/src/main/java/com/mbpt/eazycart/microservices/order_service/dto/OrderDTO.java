package com.mbpt.eazycart.microservices.order_service.dto;

import com.mbpt.eazycart.microservices.order_service.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    // For update operations, ID must not be null.
    // For create operations, this constraint is ignored because OnCreate group is used.
    @NotNull(message = "Order ID cannot be null for an update operation", groups = ValidationGroups.OnUpdate.class)
    private Integer id;

    @NotNull(message = "Order date cannot be null")
    private Date orderDate;

    // ownerName should not be blank for both create and update (default group).
    @NotBlank(message = "Owner name cannot be blank")
    private String ownerName;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotEmpty(message = "Order must contain at least one product", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    private List<Integer> productIds;
}
