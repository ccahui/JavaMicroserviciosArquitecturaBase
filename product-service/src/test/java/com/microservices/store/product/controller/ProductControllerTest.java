package com.microservices.store.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.junit.jupiter.api.Disabled;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
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
    private Product productA;
    private Product productB;
    @BeforeEach
    void dbInsertData() {
        categoryA = new Category().builder().name("Categoria A").build();
        repositoryCategory.save(categoryA);
        productA = new Product().builder().name("Product A").price(25D).stock(10D).status(Status.CREATED).category(categoryA).build();
        productB = new Product().builder().name("Product B").price(15D).stock(1D).status(Status.CREATED).category(categoryA).build();
        repositoryProduct.saveAll(List.of(productA, productB));
    }

    @AfterEach
    void dbTruncate(){
        repositoryProduct.deleteAll();
        repositoryCategory.deleteAll();
    }

    @Test
    void all() throws Exception {
        this.mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testShowIsInStockSuccess() throws Exception {
        List<String> idsValid = List.of(productA.getId().toString(), productB.getId().toString());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("ids", idsValid);

        this.mockMvc.perform(get(PATH+"/inventory").contentType(MediaType.APPLICATION_JSON).params(params))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateSuccess() throws Exception{
        Long categoryId = categoryA.getId();
        ProductCreateDto dtoCreation = new ProductCreateDto().builder().name("Product A").price(25D).stock(10D).categoryId(categoryId).build();
        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoCreation)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        assertEquals(3, repositoryProduct.count());
    }


    @Test
    void update() throws Exception {
        Long categoryId = categoryA.getId();
        ProductCreateDto dtoCreation = new ProductCreateDto().builder().name("Producto UPDATE A").categoryId(categoryId).build();

        String id = "/"+productA.getId();
        this.mockMvc.perform(put(PATH+id).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoCreation)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(dtoCreation.getName()));

        assertEquals(2, repositoryProduct.count());
    }

    @Test
    void deleteSuccess() throws Exception{
        Long idValid = productA.getId();
        this.mockMvc.perform(delete(PATH+"/{id}", idValid)).andExpect(status().isNoContent());
        assertEquals(1, repositoryProduct.count());
    }
    @Test
    void deleteErrorIdInvalid() throws Exception{
        Long idInvalid = 99999L;
        this.mockMvc.perform(delete(PATH+"/{id}", idInvalid)).andExpect(status().is5xxServerError());

    }
}