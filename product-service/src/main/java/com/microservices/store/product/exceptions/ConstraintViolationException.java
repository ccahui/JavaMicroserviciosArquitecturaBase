package com.microservices.store.product.exceptions;

public class ConstraintViolationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String DESCRIPTION = "Constraint Exception (400)";

    public ConstraintViolationException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}