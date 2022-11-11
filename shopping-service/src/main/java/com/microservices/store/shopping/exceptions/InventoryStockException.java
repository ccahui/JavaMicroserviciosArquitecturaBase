package com.microservices.store.shopping.exceptions;


public class InventoryStockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String DESCRIPTION = "Product is not in stock";

    public InventoryStockException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}