package com.microservices.store.product.mapper;

import com.microservices.store.product.dto.CategoryCreateDto;
import com.microservices.store.product.dto.CategoryDto;
import com.microservices.store.product.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Test
    void whenDtoCreateToEntity_ThenReturnEntity() {
        CategoryCreateDto dto = new CategoryCreateDto().builder()
                .name("Categoria A")
                .build();
        Category entity = mapper.dtoCreateToEntity(dto);
        assertEquals(dto.getName(), entity.getName());
    }


}