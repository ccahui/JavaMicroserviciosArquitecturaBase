package com.microservices.store.shopping.client;

import com.microservices.store.shopping.client.dto.CustomerDto;
import com.microservices.store.shopping.client.dto.InventoryDto;
import com.microservices.store.shopping.client.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping(value = "/api/products/inventory")
    public List<InventoryDto> isInStock(@RequestParam List<Long>ids);
    @GetMapping(value = "/api/products")
    public List<ProductDto> findAll(@RequestParam List<Long>ids);
}
