package com.microservices.store.shopping.config;


import com.microservices.store.shopping.entity.Invoice;
import com.microservices.store.shopping.entity.InvoiceItem;
import com.microservices.store.shopping.repository.InvoiceItemsRepository;
import com.microservices.store.shopping.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;


@Configuration
@RequiredArgsConstructor
@Profile("!test") // No se ejecuta en el entorno de pruebas
public class InitData implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(InitData.class);
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemsRepository invoiceItemRepository;

    @Override
    public void run(String... args) throws Exception {

        InvoiceItem itemA1 = new InvoiceItem().builder().price(5d).productId(1L).quantity(8d).build();
        InvoiceItem itemA2 = new InvoiceItem().builder().price(50d).productId(2L).quantity(100d).build();
        InvoiceItem itemA3 = new InvoiceItem().builder().price(40d).productId(3L).quantity(10d).build();

        InvoiceItem itemB1 = new InvoiceItem().builder().price(5d).productId(1L).quantity(8d).build();
        InvoiceItem itemB2 = new InvoiceItem().builder().price(50d).productId(2L).quantity(8d).build();

        List<InvoiceItem> itemsA = List.of(itemA1, itemA2, itemA3);
        List<InvoiceItem> itemsB = List.of(itemB1, itemB2);

        Invoice invoiceA = new Invoice().builder().description("Invoice A").customerId(1L).items(itemsA).build();
        Invoice invoiceB = new Invoice().builder().description("Invoice B").customerId(2L).items(itemsB).build();

        invoiceRepository.saveAll(List.of(invoiceA, invoiceB));

        log.info("Facturas Creadas "+invoiceRepository.findAll());

    }


}
