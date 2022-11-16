package com.microservices.store.product.exceptions;


import com.microservices.store.product.exceptions.validation.ErrorMessageFields;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;




@ControllerAdvice
public class ApiExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ org.springframework.dao.DuplicateKeyException.class,
			org.springframework.web.HttpRequestMethodNotSupportedException.class,
			org.springframework.web.bind.MissingRequestHeaderException.class,
			MethodArgumentNotValidException.class,
			org.springframework.web.bind.MissingServletRequestParameterException.class,
			org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
			org.springframework.http.converter.HttpMessageNotReadableException.class,
			ConstraintViolationException.class,
			BindException.class
	})
	@ResponseBody
	public ErrorMessage badRequest(HttpServletRequest request, Exception exception) {
		// Validaciones de los campos.
		if (exception instanceof BindException) {
			return validacionesDatos(exception, request);
		}

		return new ErrorMessage(exception, request.getRequestURI());
	}
	private ErrorMessageFields validacionesDatos(Exception exception, HttpServletRequest request) {
		BindException ex = (BindException) exception;
		return new ErrorMessageFields(ex, request.getRequestURI());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public Object exception(HttpServletRequest request, Exception exception) {

		return new ErrorMessage(exception, request.getRequestURI());
	}




}
