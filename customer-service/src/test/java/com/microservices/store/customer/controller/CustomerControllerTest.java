package com.microservices.store.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.store.customer.entity.Customer;
import com.microservices.store.customer.repository.CustomerRepository;
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
import org.springframework.util.MimeTypeUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class CustomerControllerTest {

    private String PATH =  "/api"+CustomerController.CUSTOMER;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerRepository repository;
    private Customer dto;
    private Customer entity;

    @BeforeEach
    void setInsertData(){
        dto = new Customer().builder().firstName("Juan").lastName("Paredes").email("paredes@gmail.com").photoUrl("dtoPhoto").build();
        entity = Customer.builder().firstName("Jose").lastName("Laura").email("jose@gmail.com").photoUrl("entityPhoto").build();

        repository.save(entity);
    }
    @AfterEach
    void setTruncate(){
        repository.deleteAll();
    }

    @Test
    void shouldSuccessAll() throws Exception{
        this.mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldSuccesShow() throws Exception{
        Long idValid = entity.getId();
        this.mockMvc.perform(get(PATH+"/{id}", idValid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void shouldErrorShowIdInvalid() throws Exception{
        Long idInvalid = 9999L;
        this.mockMvc.perform(get(PATH+"/{id}", idInvalid))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldSaveSuccessfully() throws Exception {
        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        assertEquals(2, repository.count());
    }

    @Test
    void shouldUpdateSuccessfully() throws Exception {
        String id = "/"+entity.getId();
        this.mockMvc.perform(put(PATH+id).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.photoUrl").value(dto.getPhotoUrl()));

        assertEquals(1, repository.count());
    }

    @Test
    void shouldDeleteSuccess() throws Exception{
        Long idValid = entity.getId();
        this.mockMvc.perform(delete(PATH+"/{id}", idValid)).andExpect(status().isNoContent());
        assertEquals(0, repository.count());
    }
    @Test
    void shouldDeleteErrorIdInvalid() throws Exception{
        Long idInvalid = 99999L;
        this.mockMvc.perform(delete(PATH+"/{id}", idInvalid)).andExpect(status().is5xxServerError());

    }
}