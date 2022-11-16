package com.microservices.store.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {
    @NotBlank
    private String name;
    private String description;
    private Double stock;
    private Double price;
    @NotNull
    private Long categoryId;
}
