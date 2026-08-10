package com.midterm.midterm.services;


import com.midterm.midterm.dto.request.ProductRequest;
import com.midterm.midterm.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();

    ProductResponse getById(Long id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

    List<ProductResponse> getExpiredProducts();

    List<ProductResponse> getByCategory(Long catId);

    // "Buy" - decrements stock quantity when a User purchases
    ProductResponse buy(Long id, int quantity);
}