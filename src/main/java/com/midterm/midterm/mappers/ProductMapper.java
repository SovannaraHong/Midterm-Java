package com.midterm.midterm.mappers;

import com.midterm.midterm.dto.request.ProductRequest;
import com.midterm.midterm.dto.response.ProductResponse;
import com.midterm.midterm.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.catId", target = "catId")
    @Mapping(source = "category.categoryName", target = "categoryName")
    ProductResponse toResponse(Product product);

    @Mapping(target = "category", ignore = true)
        // category is set manually in the service (needs a repository lookup by catId)
    Product toEntity(ProductRequest request);
}