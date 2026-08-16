package com.midterm.midterm.services;

import com.midterm.midterm.dto.response.SaleDetailResponse;
import com.midterm.midterm.entities.SaleDetail;

import java.util.List;

public interface SaleDetailService {


  List<SaleDetailResponse> getAll();

SaleDetailResponse getById(Long id);

    void delete(Long id);
}
