package com.midterm.midterm.services.serviceimpl;


import com.midterm.midterm.dto.request.CategoryRequest;
import com.midterm.midterm.dto.response.CategoryResponse;
import com.midterm.midterm.entities.Category;

import com.midterm.midterm.exception.ResourceNotFoundException;
import com.midterm.midterm.mappers.CategoryMapper;
import com.midterm.midterm.repository.CategoryRepository;
import com.midterm.midterm.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Category", id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Category  " + id));
        existing.setCategoryName(request.getCategoryName());
        return categoryMapper.toResponse(categoryRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw ResourceNotFoundException.notFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}