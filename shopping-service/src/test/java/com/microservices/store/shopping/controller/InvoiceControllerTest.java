package com.microservices.store.shopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.store.shopping.client.CustomerClient;
import com.microservices.store.shopping.client.ProductClient;
import com.microservices.store.shopping.client.dto.CustomerDto;
import com.microservices.store.shopping.client.dto.InventoryDto;
import com.microservices.store.shopping.client.dto.ProductDto;
import com.microservices.store.shopping.config.InitData;
import com.microservices.store.shopping.entity.Invoice;
import com.microservices.store.shopping.entity.InvoiceItem;
import com.microservices.store.shopping.repository.InvoiceItemsRepository;
import com.microservices.store.shopping.repository.InvoiceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class InvoiceControllerTest {

    private String PATH =  "/api"+InvoiceController.INVOICE;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private InvoiceRepository repository;
    @Autowired
    private InvoiceItemsRepository repositoryItem;
    private Invoice dto;
    private Invoice entity;

    @MockBean
    private CustomerClient customerClient;

    @MockBean
    private ProductClient productClient;
    @BeforeEach
    void setInsertData(){

        InvoiceItem itemDto = new InvoiceItem().builder().price(5d).productId(1L).quantity(8d).build();
        InvoiceItem itemDto2 = new InvoiceItem().builder().price(50d).productId(1L).quantity(10d).build();
        InvoiceItem itemEntity = new InvoiceItem().builder().price(3d).productId(1L).quantity(8d).build();

        List<InvoiceItem> itemsDto = List.of(itemDto, itemDto2);
        List<InvoiceItem> itemsEntity = List.of(itemEntity);


        dto = new Invoice().builder().description("Invoice A").customerId(1L).items(itemsDto).build();
        entity = new Invoice().builder().description("Invoice B").customerId(2L).items(itemsEntity).build();

        repository.save(entity);
    }
    @AfterEach
    void setTruncate(){
        repositoryItem.deleteAll();
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

       // Set up mock responses for customerClient
       CustomerDto customerDto = new CustomerDto();
       // Set up the desired behavior of the customerClient mock
       when(customerClient.show(idValid, "token")).thenReturn(customerDto);

       // Set up mock responses for productClient
       List<ProductDto> products = new ArrayList<>();
       // Set up the desired behavior of the productClient mock
       when(productClient.findAll(anyList())).thenReturn(products);

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
        List<Long> productIds = Arrays.asList(1L, 1L);
        List<InventoryDto> inventoryDtos = Arrays.asList(
                new InventoryDto(1L, true, 5.0),
                new InventoryDto(1L, true, 10.0)
        );
        when(productClient.isInStock(productIds)).thenReturn(inventoryDtos);


        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        assertEquals(2, repository.count());
        assertEquals(3, repositoryItem.count());
    }

    @Test
    void shouldSaveNotStockSuccessfully() throws Exception {
        List<Long> productIds = Arrays.asList(1L, 1L);
        List<InventoryDto> inventoryDtos = Arrays.asList(
                new InventoryDto(1L, false, 5.0),
                new InventoryDto(1L, true, 10.0)
        );
        when(productClient.isInStock(productIds)).thenReturn(inventoryDtos);


        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Product is not in stock. Not Stock. Please try again later"));
    }

    @Test
    void shouldUpdateSuccessfully() throws Exception {

        Long idValid = entity.getId();
        this.mockMvc.perform(put(PATH+"/{id}", idValid).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        assertEquals(1, repository.count());
        assertEquals(2, repositoryItem.count());
    }
    @Test
    void shouldUpdateErrorIdInvalid() throws Exception{
        Long idInvalid = 99999L;
        this.mockMvc.perform(put(PATH+"/{id}", idInvalid).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))).andExpect(status().is5xxServerError());

    }

    @Test
    void shouldDeleteSuccess() throws Exception{
        Long idValid = entity.getId();
        this.mockMvc.perform(delete(PATH+"/{id}", idValid)).andExpect(status().isNoContent());
        assertEquals(0, repository.count());
        assertEquals(0, repositoryItem.count());
    }
    @Test
    void shouldDeleteErrorIdInvalid() throws Exception{
        Long idInvalid = 99999L;
        this.mockMvc.perform(delete(PATH+"/{id}", idInvalid)).andExpect(status().is5xxServerError());

    }
}