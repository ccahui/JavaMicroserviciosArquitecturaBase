package com.microservices.store.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.store.product.dto.CategoryCreateDto;
import com.microservices.store.product.dto.ProductCreateDto;
import com.microservices.store.product.entity.Category;
import com.microservices.store.product.entity.Product;
import com.microservices.store.product.repository.CategoryRepository;
import com.microservices.store.product.repository.ProductRepository;
import com.microservices.store.product.utils.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test.yml")
class ProductControllerTest {

    private String PATH =  "/api/"+ProductController.PRODUCT;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ProductRepository repositoryProduct;
    @Autowired
    private CategoryRepository repositoryCategory;
    private Category categoryA;
    @BeforeEach
    void dbInsertData() {
        categoryA = new Category().builder().name("Categoria A").build();
        repositoryCategory.save(categoryA);
    }

    @AfterEach
    void dbTruncate(){
        repositoryProduct.deleteAll();
        repositoryCategory.deleteAll();
    }

    @Test
    void all() {
    }

    @Test
    void show() {
    }

    @Test
    void testCreateSuccess() throws Exception{
        Long categoryId = categoryA.getId();
        ProductCreateDto dtoCreation = new ProductCreateDto().builder().name("Producto A").categoryId(categoryId).build();
        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoCreation)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}