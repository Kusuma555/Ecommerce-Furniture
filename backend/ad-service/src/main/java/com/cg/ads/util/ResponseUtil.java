package com.cg.ads.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cg.ads.dto.ResponseObject;

@Component
public class ResponseUtil {
	public ResponseEntity<?> createSuccessResponse(Object data, String url, HttpStatus status) {
		com.cg.ads.dto.ResponseObject response = new ResponseObject();
		response.setData(data);
		response.setError(false);
		response.setMessage("success");
		response.setUrl(url);

		return new ResponseEntity<ResponseObject>(response, status);
	}

	public ResponseEntity<?> createErrorResponse(String message, String url, HttpStatus status) {
		ResponseObject response = new ResponseObject();
		response.setError(true);
		response.setMessage(message);
		response.setUrl(url);

		return new ResponseEntity<ResponseObject>(response, status);

	}

}
