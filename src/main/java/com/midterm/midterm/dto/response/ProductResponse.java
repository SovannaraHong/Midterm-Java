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
    private String pName;
    private Integer sQty;
    private BigDecimal price;
    private LocalDate expiredDate;
    private Long catId;
    private String categoryName;
}