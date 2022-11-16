package com.microservices.store.product.service;


import com.microservices.store.product.dto.InventoryDto;
import com.microservices.store.product.dto.ProductCreateDto;
import com.microservices.store.product.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto save(ProductCreateDto productCreateDto);
    ProductDto show(Long id);
    List<ProductDto> all(List<Long> idsProduct);
    List<InventoryDto> findByIds(List<Long> idsProduct);
    ProductDto update(Long id, ProductCreateDto productCreateDto);
    void delete(Long id);
}
