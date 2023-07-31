package com.microservices.store.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.store.product.dto.CategoryCreateDto;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class CategoryControllerTest {

    private String PATH =  "/api/"+CategoryController.CATEGORY;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CategoryRepository repositoryCategory;


    @Autowired
    private ProductRepository repository;
    private Category categoriaWithProducts;
    private Category categoryWithoutProducts;

    @BeforeEach
    void dbInsertData() {
        categoriaWithProducts = new Category().builder().name("Categoria A").build();
        categoryWithoutProducts = new Category().builder().name("Categoria B").build();

        repositoryCategory.saveAll(List.of(categoriaWithProducts, categoryWithoutProducts));

        Product productA = new Product().builder().name("Product A").price(25D).stock(10D).status(Status.CREATED).category(categoriaWithProducts).build();
        Product productB = new Product().builder().name("Product B").price(15D).stock(1D).status(Status.CREATED).category(categoriaWithProducts).build();


        repository.saveAll(List.of(productA, productB));
    }


    @AfterEach
    void dbTruncate(){
        repository.deleteAll();
        repositoryCategory.deleteAll();
    }

    @Test
    void all() throws Exception {
        this.mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void show() throws Exception {
        Long idValid = categoriaWithProducts.getId();
        this.mockMvc.perform(get(PATH+"/{id}", idValid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(categoriaWithProducts.getName()));
    }

    @Test
    void testCreateSuccess() throws Exception{
        CategoryCreateDto dtoCreation = new CategoryCreateDto("Category A");
        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoCreation)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        assertEquals(3, repositoryCategory.count());
    }
    @Test
    void testCreateBadRequestNameIsBlank() throws Exception{

        CategoryCreateDto dtoCreation = new CategoryCreateDto("");
        /*
         * {"exception":"MethodArgumentNotValidException","message":"Bad Request, fields Error","path":"/api/v1/checkList-formatos",
         * "fieldsError":[{"name":"codigo","rejectValue":"","message":"must not be blank"}]
         * }
         * */
        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoCreation)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldsError[0].name").value("name"))
                .andExpect(jsonPath("$.fieldsError[0].message").value("must not be blank"));

    }


    @Test
    void update() throws Exception {
        CategoryCreateDto dtoCreation = new CategoryCreateDto("Category UPDATE A");
        String id = "/"+categoriaWithProducts.getId();
        this.mockMvc.perform(put(PATH+id).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoCreation)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(dtoCreation.getName()));

        assertEquals(2, repository.count());
    }

    @Test
    void testDeleteCategoryWithoutProductsSuccess() throws Exception{
        this.mockMvc.perform(delete(PATH+"/{id}", categoryWithoutProducts.getId())).andExpect(status().isNoContent());
    }
    @Test
    void testDeleteCategoryWithProductsError() throws Exception{
        this.mockMvc.perform(delete(PATH+"/{id}", categoriaWithProducts.getId())).andExpect(status().is5xxServerError());
    }
    @Test
    void testDeleteCategoryIDInvalidSuccess() throws Exception{
        Long idCategoryInvalid = 9999L;
        this.mockMvc.perform(delete(PATH+"/{id}",idCategoryInvalid)).andExpect(status().is5xxServerError());
    }
}