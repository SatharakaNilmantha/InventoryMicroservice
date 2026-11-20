package com.Order.Order.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private long id;

    private String itemId;
    private String orderDate;
    private double amount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}