package com.midterm.midterm.services.serviceimpl;


import com.midterm.midterm.dto.request.ProductRequest;
import com.midterm.midterm.dto.response.ProductResponse;
import com.midterm.midterm.entities.Category;
import com.midterm.midterm.entities.Product;
import com.midterm.midterm.exception.ResourceNotFoundException;
import com.midterm.midterm.mappers.ProductMapper;
import com.midterm.midterm.repository.CategoryRepository;
import com.midterm.midterm.repository.ProductRepository;
import com.midterm.midterm.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCatId())
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Category not found with id: " + request.getCatId()));

        Product product = productMapper.toEntity(request);
        product.setCategory(category);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        Category category = categoryRepository.findById(request.getCatId())
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Category not found with id: " + request.getCatId()));

        existing.setPName(request.getProductName());
        existing.setSQty(request.getSQty());
        existing.setPrice(request.getPrice());
        existing.setExpiredDate(request.getExpiredDate());
        existing.setCategory(category);

        return productMapper.toResponse(productRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw ResourceNotFoundException.notFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> getExpiredProducts() {
        return productRepository.findByExpiredDateBefore(LocalDate.now())
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getByCategory(Long catId) {
        return productRepository.findByCategory_CatId(catId)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse buy(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Product not found with id: " + id));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (product.getSQty() < quantity) {
            throw new IllegalArgumentException("Not enough stock. Available: " + product.getSQty());
        }

        product.setSQty(product.getSQty() - quantity);
        return productMapper.toResponse(productRepository.save(product));
    }
}