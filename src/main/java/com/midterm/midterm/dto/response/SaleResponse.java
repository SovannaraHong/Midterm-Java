package com.midterm.midterm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {

    private Long saleId;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;

    private Long staffId;
    private String staffName;

    private List<SaleDetailResponse> details;
}