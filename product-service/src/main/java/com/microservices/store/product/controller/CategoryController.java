package com.microservices.store.product.controller;

import com.microservices.store.product.dto.CategoryCreateDto;
import com.microservices.store.product.dto.CategoryDto;
import com.microservices.store.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api" + CategoryController.CATEGORY)
@RequiredArgsConstructor
@Tag(name="CategoryController", description="The Categories api")
public class CategoryController {
    public static final String CATEGORY = "/categories";
    private final CategoryService service;

    @Operation(summary = "Info", description = "Categories info", tags = { "CategoryController" })
    @GetMapping
    public List<CategoryDto> all() {
        return service.all();
    }

    @Operation(summary = "Category Info", description = "Get the category info through their id", tags = { "CategoryController" })
    @GetMapping(value = {"/{id}"})
    public CategoryDto show(@Parameter(description="Category id", required = true, example="3", in = ParameterIn.PATH )  @PathVariable long id) {
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
    @Operation(summary = "Delete category", description = "Delete an exists category", tags = { "CategoryController" })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description="Category id", required = true, example="2", in = ParameterIn.PATH ) @PathVariable long id) {
        this.service.delete(id);
    }

}
