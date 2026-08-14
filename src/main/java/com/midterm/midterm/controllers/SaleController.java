package com.midterm.midterm.controllers;

import com.midterm.midterm.dto.request.SaleRequest;
import com.midterm.midterm.dto.response.SaleResponse;
import com.midterm.midterm.services.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponse> create(@Valid @RequestBody SaleRequest request) {
        SaleResponse response = saleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> getAll() {
        return ResponseEntity.ok(saleService.getAll());
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<SaleResponse>> getByStaff(@PathVariable Long staffId) {
        return ResponseEntity.ok(saleService.getByStaff(staffId));
    }
}