package com.microservices.store.product.mapper;


import com.microservices.store.product.dto.CategoryCreateDto;
import com.microservices.store.product.dto.CategoryDto;
import com.microservices.store.product.entity.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper mapper;

    public CategoryDto entityToDto(Category entity){
        return mapper.map(entity, CategoryDto.class);
    }

    public Category dtoToEntity(CategoryDto dto){
        return mapper.map(dto, Category.class);
    }
    public Category dtoCreateToEntity(CategoryCreateDto dto){
        return mapper.map(dto, Category.class);
    }

}
