package com.microservices.store.shopping.service;

import com.microservices.store.shopping.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    public List<Invoice> all();
    public Invoice save(Invoice invoice);
    public Invoice update(Long id, Invoice invoice);
    public void delete(Long id);
    public Invoice show(Long id);
}