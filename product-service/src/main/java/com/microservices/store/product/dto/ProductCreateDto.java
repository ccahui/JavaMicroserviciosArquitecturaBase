package com.microservices.store.product.dto;

import com.microservices.store.product.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {
    private String name;
    private String description;
    private Double stock;
    private Double price;
}
