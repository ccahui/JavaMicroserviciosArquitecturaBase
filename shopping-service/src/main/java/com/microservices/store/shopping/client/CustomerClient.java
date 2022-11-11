package com.microservices.store.shopping.client;

import com.microservices.store.shopping.client.dto.CustomerDto;
import com.microservices.store.shopping.client.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @GetMapping(value = "/api/customers/{id}")
    public CustomerDto show(@PathVariable Long id);
}
