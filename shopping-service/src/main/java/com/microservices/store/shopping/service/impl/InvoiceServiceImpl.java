package com.microservices.store.shopping.service.impl;


import com.microservices.store.shopping.client.CustomerClient;
import com.microservices.store.shopping.client.ProductClient;
import com.microservices.store.shopping.client.dto.CustomerDto;
import com.microservices.store.shopping.client.dto.InventoryDto;
import com.microservices.store.shopping.client.dto.ProductDto;
import com.microservices.store.shopping.entity.Invoice;
import com.microservices.store.shopping.entity.InvoiceItem;
import com.microservices.store.shopping.exceptions.InventoryStockException;
import com.microservices.store.shopping.exceptions.NotFoundException;
import com.microservices.store.shopping.repository.InvoiceItemsRepository;
import com.microservices.store.shopping.repository.InvoiceRepository;
import com.microservices.store.shopping.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemsRepository invoiceItemsRepository;
    private final ProductClient productClient;
    private final CustomerClient customerClient;

    @Override
    public List<Invoice> all() {
        return invoiceRepository.findAll();
    }


    @Override
    public Invoice save(Invoice invoice) {
        List<Long> idsProduct = invoice.getItems().stream().map(e -> e.getProductId()).collect(Collectors.toList());
        List<InventoryDto> inventoryDtos = productClient.isInStock(idsProduct);

        boolean allProductsInStock = inventoryDtos.stream().allMatch(InventoryDto::isInStock);
        if (idsProduct.size() != inventoryDtos.size() || !allProductsInStock) {
            throw new InventoryStockException("Not Stock. Please try again later");
        }
        invoice.setState("CREATED");
        return invoiceRepository.save(invoice);
    }


    @Override
    public Invoice update(Long id, Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findById(id).orElseThrow(() -> new NotFoundException("Invoice not Found " + id));


        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());

        // Eliminar los items y asociar con los nuevos items
        invoiceDB.getItems().clear();
        List<InvoiceItem> newItems = invoice.getItems();
        newItems.forEach((newItem) -> {
            invoiceDB.getItems().add(newItem);
        });

        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public void delete(Long id) {
        invoiceRepository.findById(id).orElseThrow(() -> new NotFoundException("Invoice not Found " + id));
        invoiceRepository.deleteById(id);
    }

    @Override
    public Invoice show(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new NotFoundException("Invoice not Found " + id));
        List<Long> idsProduct = invoice.getItems().stream().map(e -> e.getProductId()).collect(Collectors.toList());

        CustomerDto customer = customerClient.show(invoice.getCustomerId());
        List<ProductDto> products = productClient.findAll(idsProduct);

        invoice.setCustomer(customer);

        invoice.getItems().stream().forEach(invoiceItem -> {
            Predicate<ProductDto> isEquals = p -> p.getId() == invoiceItem.getProductId();
            products.stream().filter(isEquals)
                    .findFirst()
                    .ifPresent((product) -> {
                        invoiceItem.setProduct(product);
                    });

        });
        return invoice;
    }
}