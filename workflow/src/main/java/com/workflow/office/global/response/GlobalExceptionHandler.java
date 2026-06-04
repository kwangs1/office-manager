package com.workflow.office.global.response;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.workflow.office.global.response.dto.DataNotFoundException;
import com.workflow.office.global.response.dto.ErrorResponse;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	
	private final MessageSource messageSource;
	
	private String getMsg(String code) {
		try {
			return messageSource.getMessage(code, null, Locale.KOREA);
		} catch (Exception e) {
			return getMsg("SERVER_500");
		}	
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e){
		e.printStackTrace();
		
		ErrorCode ec = ErrorCode.METHOD_NOT_ALLOWED;
		ErrorResponse response = new ErrorResponse(ec.getCode(), getMsg(ec.getMessageKey()));
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e){
		e.printStackTrace();
		
		ErrorCode ec = ErrorCode.TYPE_MISMATCH;
		ErrorResponse response = new ErrorResponse(ec.getCode(), getMsg(ec.getMessageKey()));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleDataNotFound(DataNotFoundException e){
		e.printStackTrace();

		ErrorCode ec = ErrorCode.DATA_NOT_FOUND;
		String msg = (e.getMessage() != null) ? e.getMessage() : getMsg(ec.getMessageKey());
		ErrorResponse response = new ErrorResponse(ec.getCode(), msg);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e){
		e.printStackTrace();
		
		ErrorCode ec = ErrorCode.SERVER_ERROR;
		ErrorResponse response = new ErrorResponse(ec.getCode(), getMsg(ec.getMessageKey()));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler({RuntimeException.class, IllegalArgumentException.class, IllegalStateException.class})
	protected ResponseEntity<ErrorResponse> handleBusinessException(Exception e){
		e.printStackTrace();
		
		String msg;
		try {
			msg = messageSource.getMessage(e.getMessage(), null, Locale.KOREA);
		} catch(Exception ex) {
			msg = e.getMessage();
		}
		ErrorResponse response = new ErrorResponse(ErrorCode.BUSINESS_ERROR.getCode(), msg);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}
