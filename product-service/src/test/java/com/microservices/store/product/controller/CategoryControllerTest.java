package com.microservices.store.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.store.product.dto.CategoryCreateDto;
import com.microservices.store.product.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test.yml")
class CategoryControllerTest {

    private String PATH =  "/api/"+CategoryController.CATEGORY;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CategoryRepository repositoryCategory;
    @Test
    void all() {
    }

    @Test
    void show() {
    }

    @Test
    void testCreateSuccess() throws Exception{
        CategoryCreateDto dtoCreation = new CategoryCreateDto("Category A");
        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoCreation)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
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
    void update() {
    }

    @Test
    void delete() {
    }
}