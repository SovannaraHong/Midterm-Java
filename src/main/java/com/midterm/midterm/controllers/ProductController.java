package com.midterm.midterm.controllers;

import com.midterm.midterm.dto.request.ProductRequest;
import com.midterm.midterm.dto.response.ProductResponse;
import com.midterm.midterm.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Admin feature: expired products
    @GetMapping("/expired")
    public ResponseEntity<List<ProductResponse>> getExpiredProducts() {
        return ResponseEntity.ok(productService.getExpiredProducts());
    }

    // Filter by category
    @GetMapping("/category/{catId}")
    public ResponseEntity<List<ProductResponse>> getByCategory(@PathVariable Long catId) {
        return ResponseEntity.ok(productService.getByCategory(catId));
    }

    // User feature: buy - decrements stock
    @PostMapping("/{id}/buy")
    public ResponseEntity<ProductResponse> buy(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(productService.buy(id, quantity));
    }
}