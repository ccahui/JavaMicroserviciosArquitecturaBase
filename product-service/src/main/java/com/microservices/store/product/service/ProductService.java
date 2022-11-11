package com.microservices.store.product.service;


import com.microservices.store.product.dto.*;

import java.util.List;

public interface ProductService {
    ProductDto save(ProductCreateDto productCreateDto);
    ProductDto show(Long id);
    List<ProductDto> all();
    List<InventoryDto> findByIds(List<Long> idsProduct);
    ProductDto update(Long id, ProductCreateDto productCreateDto);
    void delete(Long id);
}
