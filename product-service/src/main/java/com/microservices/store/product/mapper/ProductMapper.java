package com.microservices.store.product.mapper;


import com.microservices.store.product.dto.ProductCreateDto;
import com.microservices.store.product.dto.ProductDto;
import com.microservices.store.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ModelMapper mapper;

    public ProductDto entityToDto(Product entity){
        ProductDto dto = mapper.map(entity, ProductDto.class);
        return dto;
    }

    public Product dtoToEntity(ProductDto dto){
        Product entity = mapper.map(dto, Product.class);
        return entity;
    }
    public Product dtoCreateToEntity(ProductCreateDto dto){
        Product entity = mapper.map(dto, Product.class);
        return entity;
    }

}
