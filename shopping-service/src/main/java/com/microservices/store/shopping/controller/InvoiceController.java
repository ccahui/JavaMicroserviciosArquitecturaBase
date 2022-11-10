package com.microservices.store.shopping.controller;

import com.microservices.store.shopping.entity.Invoice;
import com.microservices.store.shopping.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api" + InvoiceController.INVOICE)
@RequiredArgsConstructor
public class InvoiceController {
    public static final String INVOICE = "/invoices";

    private final InvoiceService invoiceService;

    @GetMapping
    public List<Invoice> all() {
        return invoiceService.all();
    }

    @GetMapping(value = "/{id}")
    public Invoice show(@PathVariable Long id) {
        log.info("Fetching Invoice with id {}", id);
        return invoiceService.show(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice create(@RequestBody Invoice invoice) {
        log.info("Creating Invoice : {}", invoice);
        return invoiceService.save(invoice);
    }

    @PutMapping(value = "/{id}")
    public Invoice updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        log.info("Updating Invoice with id {}", id);
        return invoiceService.update(id, invoice);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Fetching & Deleting Invoice with id {}", id);
        invoiceService.delete(id);
    }

}