package com.microservices.store.customer.controller;

import com.microservices.store.customer.entity.Customer;
import com.microservices.store.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;


    @GetMapping
    public List<Customer> all() {
        return  customerService.all();
    }

    @GetMapping(value = "/{id}")
    public Customer show(@PathVariable Long id) {
        log.info("Fetching Customer with id {}", id);
        return customerService.show(id);
    }

    @PostMapping
    public Customer save(@RequestBody Customer customer) {
        log.info("Creating Customer : {}", customer);
        return customerService.save(customer);
    }

    @PutMapping(value = "/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        log.info("Updating Customer with id {}", id);
        return customerService.update(id, customer);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        log.info("Fetching & Deleting Customer with id {}", id);
        customerService.delete(id);
    }


}