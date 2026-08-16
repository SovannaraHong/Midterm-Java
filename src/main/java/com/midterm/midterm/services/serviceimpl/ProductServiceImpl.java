package com.midterm.midterm.services.serviceimpl;

import com.midterm.midterm.dto.request.ProductRequest;
import com.midterm.midterm.dto.response.ProductResponse;
import com.midterm.midterm.entities.Category;
import com.midterm.midterm.entities.Product;
import com.midterm.midterm.exception.ResourceNotFoundException;
import com.midterm.midterm.mappers.ProductMapper;
import com.midterm.midterm.repository.CategoryRepository;
import com.midterm.midterm.repository.ProductRepository;
import com.midterm.midterm.services.FileStorageService;
import com.midterm.midterm.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final FileStorageService fileStorageService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Value("${app.upload.base-url}")
    private String uploadBaseUrl;

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = findProductOrThrow(id);
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Category category = findCategoryOrThrow(request.getCatId());

        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        product.setSoldQty(0);
        if (product.getExpiredDate() == null) {
            product.setExpiredDate(LocalDate.now().plusMonths(3));
        }
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product existing = findProductOrThrow(id);
        Category category = findCategoryOrThrow(request.getCatId());

        existing.setProductName(request.getProductName());
        existing.setDescription(request.getDescription());
        existing.setSQty(request.getSQty());
        existing.setStatus(request.getStatus());
        existing.setPrice(request.getPrice());
        if (request.getExpiredDate() != null) {
            existing.setExpiredDate(request.getExpiredDate());
        }
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
        if (!categoryRepository.existsById(catId)) {
            throw ResourceNotFoundException.notFoundException("Category not found with id: " + catId);
        }
        return productRepository.findByCategory_CatId(catId)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getBestSeller() {
        Product product = productRepository.findTopByOrderBySoldQtyDesc()
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("No products found"));
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse getBestSellerByCategory(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw ResourceNotFoundException.notFoundException("Category not found with id: " + catId);
        }
        Product product = productRepository.findTopByCategory_CatIdOrderBySoldQtyDesc(catId)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("No products found in category: " + catId));
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse uploadImage(Long id, MultipartFile file) {
        Product product = findProductOrThrow(id);

        if (product.getImageUrl() != null && product.getImageUrl().startsWith(uploadBaseUrl)) {
            String oldFilename = product.getImageUrl().substring(product.getImageUrl().lastIndexOf('/') + 1);
            fileStorageService.delete(oldFilename);
        }

        String filename = fileStorageService.store(file);
        product.setImageUrl(uploadBaseUrl + "/" + filename);

        return productMapper.toResponse(productRepository.save(product));
    }

    private Product findProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Product not found with id: " + id));
    }

    private Category findCategoryOrThrow(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Category not found with id: " + catId));
    }
}