package com.midterm.midterm.controllers;

import com.midterm.midterm.dto.response.SaleDetailResponse;
import com.midterm.midterm.entities.SaleDetail;
import com.midterm.midterm.services.SaleDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-details")
@RequiredArgsConstructor
public class SaleDetailController {

    private final SaleDetailService saleDetailService;

    @GetMapping
    public ResponseEntity<List<SaleDetailResponse>> getAll() {
        return ResponseEntity.ok(saleDetailService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(saleDetailService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        saleDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
