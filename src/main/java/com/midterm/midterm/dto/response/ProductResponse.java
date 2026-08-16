package com.midterm.midterm.dto.response;

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
public class ProductResponse {
    private Long pid;
    private String productName;
    private Integer sQty;
    private String imageUrl;
    private Boolean status;
    private String description;
    private BigDecimal price;
    private LocalDate expiredDate;
    private Long catId;
    private String categoryName;
    private Integer soldQty;
}