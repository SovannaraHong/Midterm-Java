package com.midterm.midterm.controllers;

import com.midterm.midterm.dto.request.StaffRequest;
import com.midterm.midterm.dto.response.StaffResponse;
import com.midterm.midterm.services.StaffService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping
    public ResponseEntity<List<StaffResponse>> getAll() {
        return ResponseEntity.ok(staffService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getById(id));
    }

    @PostMapping
    public ResponseEntity<StaffResponse> create(@RequestBody StaffRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffResponse> update(@PathVariable Long id, @RequestBody StaffRequest request) {
        return ResponseEntity.ok(staffService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(
            @PathVariable Long id,
            HttpSession session
    ) {
        Long currentStaffId = (Long) session.getAttribute("staffId");

        staffService.delete(id, currentStaffId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StaffResponse> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(staffService.uploadImage(id, file));
    }
}