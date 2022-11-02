package com.microservices.store.product.service.impl;

import com.microservices.store.product.dto.CategoryCreateDto;
import com.microservices.store.product.dto.CategoryDto;
import com.microservices.store.product.entity.Category;
import com.microservices.store.product.exceptions.NotFoundException;
import com.microservices.store.product.mapper.CategoryMapper;
import com.microservices.store.product.repository.CategoryRepository;
import com.microservices.store.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceImpTest {
    @Mock
    private CategoryRepository repository;
    @Mock
    private CategoryMapper mapper;
    @InjectMocks
    private CategoryServiceImp service;

    private Category entity;
    private CategoryDto dto;

    @BeforeEach
    void setup() {
        entity = new Category().builder().name("Category A").build();
        dto = new CategoryDto().builder().name("Category A").build();
    }

    @Test
    void whenSaveSuccess_thenDto() {
        CategoryCreateDto dtoCreate = new CategoryCreateDto("Category A");

        when(mapper.dtoCreateToEntity(dtoCreate)).thenReturn(entity);
        when(repository.save(any(Category.class))).thenReturn(entity);
        when(mapper.entityToDto(entity)).thenReturn(dto);

        CategoryDto dtoService = service.save(dtoCreate);

        verify(mapper).dtoCreateToEntity(dtoCreate);
        verify(repository).save(any(Category.class));
        verify(mapper).entityToDto(entity);
        assertEquals(dtoService.getName(), dto.getName());
    }
    @Test
    void whenUpdateSuccess_thenDto() {
        CategoryCreateDto dtoCreate = new CategoryCreateDto("Category C");

        Long id = entity.getId();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.entityToDto(entity)).thenReturn(dto);
        when(repository.save(any(Category.class))).thenReturn(entity);
        CategoryDto dtoService = service.update(entity.getId(), dtoCreate);


        verify(repository).save(any(Category.class));
        verify(mapper).entityToDto(entity);


        assertEquals(dtoService.getName(), dto.getName());
    }

    @Test
    void whenShowIdValid_thenDto() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.entityToDto(entity)).thenReturn(dto);

        CategoryDto dtoService = service.show(id);

        verify(repository).findById(id);
        verify(mapper).entityToDto(entity);
        assertEquals(dtoService.getName(), dto.getName());
    }

    @Test
    void whenShowIdInvalid_thenErrorNotFound() {
        Long id = 10L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> service.show(id));

        verify(repository).findById(id);
    }

    @Test
    void whenAll_thenDtos() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.entityToDto(entity)).thenReturn(dto);

        service.all();

        verify(repository).findAll();
        verify(mapper).entityToDto(entity);

    }


}