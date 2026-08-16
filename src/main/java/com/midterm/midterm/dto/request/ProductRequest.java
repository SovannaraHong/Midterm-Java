package com.midterm.midterm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String productName;
    private Integer sQty;
    private String description;
    private Boolean status;
    private BigDecimal price;
    private LocalDate expiredDate;
    private Long catId;
    
}