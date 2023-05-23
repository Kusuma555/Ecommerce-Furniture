package com.cg.gateway.controller;

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

import com.cg.gateway.service.RemoteUploadService;

@CrossOrigin
@RestController
@RequestMapping("/upload")
public class RemoteUploadController {
	@Autowired
	private RemoteUploadService service;

	@PostMapping("/{ownerId}")
	public ResponseEntity<?> upload(@PathVariable String ownerId, @RequestParam("files") MultipartFile[] files) {
		return new ResponseEntity<>(service.upload(ownerId, files), HttpStatus.CREATED);
	}

	@DeleteMapping("/filename/{fileName}")
	public ResponseEntity<?> remove(@PathVariable String fileName) {
		return new ResponseEntity<>(service.remove(fileName), HttpStatus.OK);
	}

	@DeleteMapping("/{ownerId}")
	public ResponseEntity<?> removeAllByOwner(@PathVariable String ownerId) {
		return new ResponseEntity<>(service.removeAllByOwner(ownerId), HttpStatus.OK);
	}

	@GetMapping("/{ownerId}")
	public ResponseEntity<?> get(@PathVariable String ownerId) {
		return new ResponseEntity<>(service.get(ownerId), HttpStatus.OK);
	}

	@GetMapping("/filename/{fileName}")
	public ResponseEntity<?> getFile(@PathVariable String fileName) {
		return new ResponseEntity<>(service.getFile(fileName), HttpStatus.OK);
	}

}
