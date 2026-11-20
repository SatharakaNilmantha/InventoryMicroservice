package com.product.product.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private long id;
    private String productId;
    private String productName;
    private String description;
    private boolean forSale;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}