package com.midterm.midterm.controllers;

import com.midterm.midterm.dto.request.StaffRequest;
import com.midterm.midterm.dto.response.StaffResponse;
import com.midterm.midterm.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        staffService.delete(id);
        return ResponseEntity.noContent().build();
    }
}