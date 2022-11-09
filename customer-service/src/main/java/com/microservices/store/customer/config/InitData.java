package com.microservices.store.customer.config;

import com.microservices.store.customer.entity.Customer;
import com.microservices.store.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(InitData.class);

    private final CustomerRepository repository;

    @Override
    public void run(String... args) throws Exception {



        Customer customerA = new Customer().builder().firstName("Juan").lastName("Paredes").email("juan@gmail.com").build();
        Customer customerB = new Customer().builder().firstName("Carlos").lastName("Validivia").email("carlos@gmail.com").build();
        Customer customerC = new Customer().builder().firstName("Marcos").lastName("Sanchez").email("marcos@gmail.com").build();


        repository.saveAll(List.of(customerA, customerB, customerC));

        log.info("Clientes Creadps "+repository.findAll());

    }


}
