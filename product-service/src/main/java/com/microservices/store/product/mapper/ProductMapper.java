package com.microservices.store.product.mapper;


import com.microservices.store.product.dto.InventoryDto;
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

    public ProductDto entityToDto(Product entity) {
        return mapper.map(entity, ProductDto.class);
    }

    public Product dtoToEntity(ProductDto dto) {
        return mapper.map(dto, Product.class);
    }

    public Product dtoCreateToEntity(ProductCreateDto dto) {
        return mapper.map(dto, Product.class);
    }

    public InventoryDto entityToInventoryDto(Product entity) {
        boolean isInStock = entity.getStock() > 0;
        return new InventoryDto().builder().id(entity.getId()).price(entity.getPrice())
                            .isInStock(isInStock).build();
    }

}
