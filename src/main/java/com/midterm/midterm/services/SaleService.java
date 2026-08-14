package com.midterm.midterm.services;

import com.midterm.midterm.dto.request.SaleRequest;
import com.midterm.midterm.dto.response.SaleResponse;

import java.util.List;

public interface SaleService {

    SaleResponse create(SaleRequest request);

    SaleResponse getById(Long id);

    List<SaleResponse> getAll();

    List<SaleResponse> getByStaff(Long staffId);
}