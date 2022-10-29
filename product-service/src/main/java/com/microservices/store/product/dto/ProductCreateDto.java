package com.microservices.store.product.dto;

import com.microservices.store.product.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
