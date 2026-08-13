package com.midterm.midterm.services;


import com.midterm.midterm.dto.request.ProductRequest;
import com.midterm.midterm.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();

    ProductResponse getById(Long id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

    List<ProductResponse> getExpiredProducts();

    List<ProductResponse> getByCategory(Long catId);

    ProductResponse buy(Long id, int quantity);

    ProductResponse getBestSeller();

    ProductResponse getBestSellerByCategory(Long catId);

    ProductResponse uploadImage(Long id, MultipartFile file);
}