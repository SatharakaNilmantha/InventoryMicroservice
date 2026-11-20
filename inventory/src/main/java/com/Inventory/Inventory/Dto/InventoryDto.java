package com.Inventory.Inventory.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {
    private long id;
    private String itemId;
    private String productId;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}