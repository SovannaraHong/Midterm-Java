package com.midterm.midterm.services;


import com.midterm.midterm.dto.request.StaffRequest;
import com.midterm.midterm.dto.response.StaffResponse;

import java.util.List;

public interface StaffService {
    List<StaffResponse> getAll();

    StaffResponse getById(Long id);

    StaffResponse create(StaffRequest request);

    StaffResponse update(Long id, StaffRequest request);

    void delete(Long id);

}