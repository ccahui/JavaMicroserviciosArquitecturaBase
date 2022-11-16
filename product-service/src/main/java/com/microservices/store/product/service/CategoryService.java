package com.microservices.store.product.service;


import com.microservices.store.product.dto.CategoryCreateDto;
import com.microservices.store.product.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryCreateDto categoryCreateDto);
    CategoryDto show(Long id);
    List<CategoryDto> all();
    CategoryDto update(Long id, CategoryCreateDto categoryCreateDto);
    void delete(Long id);
}
