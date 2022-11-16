package com.microservices.store.product.controller;

import com.microservices.store.product.dto.InventoryDto;
import com.microservices.store.product.dto.ProductCreateDto;
import com.microservices.store.product.dto.ProductDto;
import com.microservices.store.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api" + ProductController.PRODUCT)
@RequiredArgsConstructor
public class ProductController {
    public static final String PRODUCT = "/products";
    private final ProductService service;

    @GetMapping
    public List<ProductDto> all(@RequestParam(required = false) List<Long> ids) {
        return service.all(ids);
    }

    @GetMapping(value="/inventory")
    public List<InventoryDto> isInStock(@RequestParam List<Long> ids) {
        return service.findByIds(ids);
    }

    @GetMapping(value = {"/{id}"})
    public ProductDto show(@PathVariable long id) {
        return service.show(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@Valid @RequestBody ProductCreateDto customer) {
        return service.save(customer);
    }

    @PutMapping(value = {"/{id}"})
    public ProductDto update(@Valid @RequestBody ProductCreateDto body, @PathVariable Long id) {
        return service.update(id, body);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }



}
