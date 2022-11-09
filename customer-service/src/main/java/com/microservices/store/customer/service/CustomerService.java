package com.microservices.store.customer.service;

import com.microservices.store.customer.entity.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> all();
    public Customer save(Customer customer);
    public Customer update(Long id, Customer customer);
    public void delete(Long id);
    public  Customer show(Long id);

}