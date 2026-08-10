package com.midterm.midterm.mappers;

import com.midterm.midterm.dto.request.CategoryRequest;
import com.midterm.midterm.dto.response.CategoryResponse;
import com.midterm.midterm.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    Category toEntity(CategoryRequest request);
}