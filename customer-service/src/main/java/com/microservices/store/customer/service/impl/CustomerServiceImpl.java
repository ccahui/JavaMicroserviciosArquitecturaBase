package com.microservices.store.customer.service.impl;

import com.microservices.store.customer.entity.Customer;
import com.microservices.store.customer.exceptions.NotFoundException;
import com.microservices.store.customer.repository.CustomerRepository;
import com.microservices.store.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl  implements CustomerService {


    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> all() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        customer.setState("CREATED");
        Customer customerDB = customerRepository.save ( customer );
        return customerDB;
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Customer customerDB = customerRepository.findById(id).orElse(null);
        if (customerDB == null){
            return  null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());

        return  customerRepository.save(customerDB);
    }

    @Override
    public void delete(Long id) {
        customerRepository.findById(id).orElseThrow(()->new NotFoundException("Customer not Found "+id));
        customerRepository.deleteById(id);
    }

    @Override
    public Customer show(Long id) {
        return  customerRepository.findById(id).orElseThrow(()->new NotFoundException("Customer not Found: "+id));
    }
}