package com.microservices.store.product.service.impl;

import com.microservices.store.product.dto.InventoryDto;
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
        product.setStatus(Status.CREATED);
        return mapper.entityToDto(repository.save(product));
    }

    @Override
    public ProductDto show(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product id (" + id + ")"));
        return mapper.entityToDto(product);
    }
    @Override
    public List<InventoryDto> findByIds(List<Long>ids) {
        List<Product> products = repository.findAllById(ids);
        return products.stream().map(mapper::entityToInventoryDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> all(List<Long>ids) {
        List<Product> products;
        if(ids == null) {
            products = repository.findAll();
        }else {
            products = repository.findAllById(ids);
        }

        return products.stream().map(mapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto update(Long id, ProductCreateDto productCreateDto) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product id (" + id + ")"));
        product.setName(productCreateDto.getName());
        product.setStock(productCreateDto.getStock());
        product.setCategory(new Category().builder().id(productCreateDto.getCategoryId()).build());
        repository.save(product);

        return mapper.entityToDto(product);
    }

    @Override
    public void delete(Long id) {
        assertProductExists(id);
        repository.deleteById(id);
    }
    public void assertProductExists(Long id) {
        repository.findById(id).orElseThrow(()->new NotFoundException("Product not found: "+id));
    }
}
