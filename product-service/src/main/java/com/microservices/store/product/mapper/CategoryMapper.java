package com.microservices.store.product.mapper;


import com.microservices.store.product.dto.CategoryDto;
import com.microservices.store.product.entity.Category;
import com.microservices.store.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper mapper;

    public CategoryDto entityToDto(Category entity){
        CategoryDto dto = mapper.map(entity, CategoryDto.class);
        return dto;
    }

    public Category dtoToEntity(CategoryDto dto){
        Category entity = mapper.map(dto, Category.class);
        return entity;
    }

}
