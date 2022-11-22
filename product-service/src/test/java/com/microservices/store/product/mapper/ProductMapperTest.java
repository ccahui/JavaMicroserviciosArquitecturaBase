package com.microservices.store.product.mapper;

import com.microservices.store.product.dto.CategoryDto;
import com.microservices.store.product.dto.ProductCreateDto;
import com.microservices.store.product.dto.ProductDto;
import com.microservices.store.product.entity.Category;
import com.microservices.store.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ProductMapperTest {
    @Autowired
    private ProductMapper mapper;

    @Test
    void whenEntityToDto_ThenReturnDto() {
        Product entity = new Product().builder()
                .id(1L)
                .name("Categoria A")
                .description("Descripcion")
                .category(new Category().builder().id(1L).build())
                .createAt(new Date())
                .build();
        ProductDto dto = mapper.entityToDto(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getCreateAt(), dto.getCreateAt());
    }

    @Test
    void whenDtoToEntity_ThenReturnEntity() {

        ProductDto dto = new ProductDto().builder()
                .id(1L)
                .name("Categoria A")
                .description("Descripcion")
                .createAt(new Date())
                .build();
        Product entity = mapper.dtoToEntity(dto);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getCreateAt(), dto.getCreateAt());

    }
    @Test
    void whenDtoCreateToEntity_ThenReturnEntity() {

        ProductCreateDto dto = new ProductCreateDto().builder()
                .name("Categoria A")
                .description("Descripcion")
                .price(4D)
                .stock(5D)
                .categoryId(2L)
                .build();
        Product entity = mapper.dtoCreateToEntity(dto);


        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getPrice(), dto.getPrice());
        assertEquals(entity.getStock(), dto.getStock());
        assertEquals(entity.getCategory().getId(), dto.getCategoryId());


    }
}