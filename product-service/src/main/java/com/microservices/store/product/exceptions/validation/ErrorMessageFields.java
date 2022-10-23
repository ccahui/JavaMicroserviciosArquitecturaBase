package com.microservices.store.product.exceptions.validation;

import com.microservices.store.product.exceptions.ErrorMessage;
import org.springframework.validation.BindException;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessageFields extends ErrorMessage {
    private static final String DESCRIPTION = "Bad Request, fields Error";

    private List<FieldError> fieldsError = new ArrayList<>();

    public ErrorMessageFields(BindException exception, String path) {

        super(exception.getClass().getSimpleName(), DESCRIPTION, path);
        this.setFieldsError(exception);
    }

    public List<FieldError> getFieldsError() {
        return fieldsError;
    }

    private void setFieldsError(BindException exception) {
        FieldError error;
        for (org.springframework.validation.FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            error = new FieldError(fieldError.getField(), fieldError.getRejectedValue(),
                    fieldError.getDefaultMessage());
            fieldsError.add(error);
        }
    }
}