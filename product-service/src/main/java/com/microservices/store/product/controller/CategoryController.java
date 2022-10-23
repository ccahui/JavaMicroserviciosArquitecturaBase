package com.microservices.store.product.controller;

import com.microservices.store.product.dto.CategoryCreateDto;
import com.microservices.store.product.dto.CategoryDto;
import com.microservices.store.product.service.CategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api" + CategoryController.CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
    public static final String CATEGORY = "/categories";
    private final CategoryService service;

    @GetMapping
    public List<CategoryDto> all() {
        return service.all();
    }

    @GetMapping(value = {"/{id}"})
    public CategoryDto show(@PathVariable long id) {
        return service.show(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody CategoryCreateDto customer) {
        return service.save(customer);
    }

    @PutMapping(value = {"/{id}"})
    public CategoryDto update(@Valid @RequestBody CategoryCreateDto body, @PathVariable Long id) {
        return service.update(id, body);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }
}
