package com.microservices.store.shopping.service.impl;


import com.microservices.store.shopping.entity.Invoice;
import com.microservices.store.shopping.entity.InvoiceItem;
import com.microservices.store.shopping.exceptions.NotFoundException;
import com.microservices.store.shopping.repository.InvoiceItemsRepository;
import com.microservices.store.shopping.repository.InvoiceRepository;
import com.microservices.store.shopping.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemsRepository invoiceItemsRepository;

    @Override
    public List<Invoice> all() {
        return  invoiceRepository.findAll();
    }


    @Override
    public Invoice save(Invoice invoice) {
        invoice.setState("CREATED");
        return invoiceRepository.save(invoice);
    }


    @Override
    public Invoice update(Long id, Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findById(id).orElseThrow(()->new NotFoundException("Invoice not Found "+id));


        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());

        // Eliminar los items y asociar con los nuevos items
        invoiceDB.getItems().clear();
        List<InvoiceItem> newItems = invoice.getItems();
        newItems.forEach((newItem)->{
                invoiceDB.getItems().add(newItem);
        });

        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public void delete(Long id) {
        invoiceRepository.findById(id).orElseThrow(()->new NotFoundException("Invoice not Found "+id));
        invoiceRepository.deleteById(id);
    }

    @Override
    public Invoice show(Long id) {
        return invoiceRepository.findById(id).orElseThrow(()->new NotFoundException("Invoice not Found "+id));
    }
}