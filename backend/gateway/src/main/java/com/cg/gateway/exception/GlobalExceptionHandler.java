package com.cg.gateway.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.gateway.dto.ResponseObject;
import com.cg.gateway.util.ResponseUtil;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private ResponseUtil responseUtil;
	@Autowired
	private Gson gson;

	@ExceptionHandler({ RuntimeException.class})
	public ResponseEntity<?> handleRuntimeException(RuntimeException ex, HttpServletRequest req) {
		ex.printStackTrace();
		try {
			String msg = ex.getMessage();
			StringBuilder sb = new StringBuilder(msg);
			msg = sb.substring(sb.indexOf("{"), sb.lastIndexOf("}") + 1);
			ResponseObject res = gson.fromJson(msg, ResponseObject.class);
			return responseUtil.createErrorResponse(res.getMessage(), req.getRequestURI(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return responseUtil.createErrorResponse(ex.getMessage(), req.getRequestURI(), HttpStatus.BAD_REQUEST);
		}
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<?> handleInternalException(Exception ex, HttpServletRequest req) {
		ex.printStackTrace();
		return responseUtil.createErrorResponse(ex.getMessage(), req.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest req) {
		String msg = ex.getMessage();

		FieldError error = ex.getFieldError();
		if (error != null)
			msg = error.getDefaultMessage();

		return responseUtil.createErrorResponse(msg, req.getRequestURI(), HttpStatus.BAD_REQUEST);
	}

}
