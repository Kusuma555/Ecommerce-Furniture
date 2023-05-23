package com.cg.upload.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResponseObject {
	private String timestamp = LocalDateTime.now().toString();
	private String message;
	private boolean error;
	private String url;
	private Object data;
}
