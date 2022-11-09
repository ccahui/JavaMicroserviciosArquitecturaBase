package com.microservices.store.customer.repository;

import com.microservices.store.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository  extends JpaRepository<Customer,Long> {
}