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
    @Mapping(source = "SQty", target = "sQty")
    ProductResponse toResponse(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "expiredDate", ignore = true)
    @Mapping(source = "SQty", target = "sQty")
    Product toEntity(ProductRequest request);
}