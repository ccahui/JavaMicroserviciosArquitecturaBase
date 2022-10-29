package com.microservices.store.product.service.impl;

import com.microservices.store.product.dto.ProductCreateDto;
import com.microservices.store.product.dto.ProductDto;
import com.microservices.store.product.entity.Category;
import com.microservices.store.product.entity.Product;
import com.microservices.store.product.exceptions.NotFoundException;
import com.microservices.store.product.mapper.ProductMapper;
import com.microservices.store.product.repository.ProductRepository;
import com.microservices.store.product.service.ProductService;
import com.microservices.store.product.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public ProductDto save(ProductCreateDto productCreateDto) {
        Product product = mapper.dtoCreateToEntity(productCreateDto);
        product.setCategory(new Category().builder().id(productCreateDto.getCategoryId()).build());
        product.setStatus(Status.CREATED);
        ProductDto dto = mapper.entityToDto(repository.save(product));
        return dto;
    }

    @Override
    public ProductDto show(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product id (" + id + ")"));
        ProductDto dto = mapper.entityToDto(product);

        return dto;
    }

    @Override
    public List<ProductDto> all() {
        List<Product> produdcts = repository.findAll();
        List<ProductDto> dtos = produdcts.stream().map(mapper::entityToDto).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public ProductDto update(Long id, ProductCreateDto productCreateDto) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product id (" + id + ")"));
        product.setName(productCreateDto.getName());
        product.setCategory(new Category().builder().id(productCreateDto.getCategoryId()).build());
        ProductDto dto = mapper.entityToDto(product);
        return dto;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
