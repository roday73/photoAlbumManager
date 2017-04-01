package com.rpo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rpo.domain.ErrorMessage;
import com.rpo.exception.ConstraintViolationException;
import com.rpo.exception.MissingDataException;
import com.rpo.exception.ObjectNotFoundException;

public interface BaseController {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public default ErrorMessage ObjectNotFoundExceptionHandler(HttpServletRequest req, ObjectNotFoundException e) 
	{
		return new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage(), req.getMethod(), req.getRequestURI().toString());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value=HttpStatus.CONFLICT)
	@ResponseBody
	public default ErrorMessage ConstraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException e) 
	{
		return new ErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage(), req.getMethod(), req.getRequestURI().toString());
	}
	
	@ExceptionHandler(MissingDataException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public default ErrorMessage MissingDataExceptionHandler(HttpServletRequest req, MissingDataException e) 
	{
		return new ErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage(), req.getMethod(), req.getRequestURI().toString());
	}

}
