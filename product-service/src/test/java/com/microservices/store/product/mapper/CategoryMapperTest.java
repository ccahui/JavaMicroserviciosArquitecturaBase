package com.microservices.store.product.mapper;

import com.microservices.store.product.dto.CategoryDto;
import com.microservices.store.product.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.yml")
class CategoryMapperTest {

    @Autowired
    private CategoryMapper mapper;

    @Test
    void whenEntityToDto_ThenReturnDto() {
        Category entity = new Category().builder()
                .id(1L)
                .name("Categoria A")
                .build();
        CategoryDto dto = mapper.entityToDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    void whenDtoToEntity_ThenReturnEntity() {
        CategoryDto dto = new CategoryDto().builder()
                .id(1L)
                .name("Categoria A")
                .build();
        Category entity = mapper.dtoToEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
    }
}