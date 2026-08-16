package com.midterm.midterm.services;


import com.midterm.midterm.dto.request.StaffRequest;
import com.midterm.midterm.dto.response.StaffResponse;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface StaffService {
    List<StaffResponse> getAll();

    StaffResponse getById(Long id);

    StaffResponse create(StaffRequest request);

    StaffResponse update(Long id, StaffRequest request);

    void delete(Long id, Long currentStaffId);
 StaffResponse uploadImage(Long id, MultipartFile file);
}