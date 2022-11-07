package com.microservices.store.product.service.impl;

import com.microservices.store.product.dto.CategoryCreateDto;
import com.microservices.store.product.dto.CategoryDto;
import com.microservices.store.product.entity.Category;
import com.microservices.store.product.exceptions.ConstraintViolationException;
import com.microservices.store.product.exceptions.NotFoundException;
import com.microservices.store.product.mapper.CategoryMapper;
import com.microservices.store.product.repository.CategoryRepository;
import com.microservices.store.product.repository.ProductRepository;
import com.microservices.store.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository repository;
    private final ProductRepository repositoryProduct;
    private final CategoryMapper mapper;

    @Override
    public CategoryDto save(CategoryCreateDto categoryCreateDto) {
        Category category = mapper.dtoCreateToEntity(categoryCreateDto);
        CategoryDto dto = mapper.entityToDto(repository.save(category));
        return dto;
    }

    @Override
    public CategoryDto show(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new NotFoundException("Category id (" + id + ")"));
        CategoryDto dto = mapper.entityToDto(category);
        return dto;
    }

    @Override
    public List<CategoryDto> all() {
        List<Category> categories = repository.findAll();
        List<CategoryDto> dtos = categories.stream().map(mapper::entityToDto).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CategoryDto update(Long id, CategoryCreateDto categoryCreateDto) {
        Category category = repository.findById(id).orElseThrow(() -> new NotFoundException("Category id (" + id + ")"));
        category.setName(categoryCreateDto.getName());
        repository.save(category);
        CategoryDto dto = mapper.entityToDto(category);
        return dto;
    }

    @Override
    public void delete(Long id) {
        assertProductsIsEmpty(id);
        repository.deleteById(id);
    }

    public void assertProductsIsEmpty(Long id) {
        if(repository.hasProducts(id)){
          throw new ConstraintViolationException("There are products with this category");
        }
    }
}
