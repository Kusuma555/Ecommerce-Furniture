package com.cg.upload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cg.upload.service.UploadService;
import com.cg.upload.util.ResponseUtil;

@CrossOrigin
@RestController
@RequestMapping("/upload")
public class UploadController {
	private static final String BASE_URL = "/api/upload";

	@Autowired
	private UploadService service;
	@Autowired
	private ResponseUtil responseUtil;

	@PostMapping("/{ownerId}")
	public ResponseEntity<?> upload(@PathVariable String ownerId, @RequestParam("files") MultipartFile[] files) {
		return responseUtil.createSuccessResponse(service.upload(ownerId, files), BASE_URL + "/" + ownerId,
				HttpStatus.CREATED);
	}

	@DeleteMapping("/filename/{fileName}")
	public ResponseEntity<?> remove(@PathVariable String fileName) {
		return responseUtil.createSuccessResponse(service.remove(fileName), BASE_URL + "/" + fileName, HttpStatus.OK);
	}

	@DeleteMapping("/{ownerId}")
	public ResponseEntity<?> removeAllByOwner(@PathVariable String ownerId) {
		return responseUtil.createSuccessResponse(service.removeAllByOwner(ownerId), BASE_URL + "/" + ownerId,
				HttpStatus.OK);
	}

	@GetMapping("/{ownerId}")
	public ResponseEntity<?> get(@PathVariable String ownerId) {
		return responseUtil.createSuccessResponse(service.get(ownerId), BASE_URL + "/" + ownerId, HttpStatus.OK);
	}

	@GetMapping("/filename/{fileName}")
	public ResponseEntity<?> getFile(@PathVariable String fileName) {
		return responseUtil.createSuccessResponse(service.getFile(fileName), BASE_URL + "/" + fileName, HttpStatus.OK);
	}
}
