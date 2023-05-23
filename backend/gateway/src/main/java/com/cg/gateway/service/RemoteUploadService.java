package com.cg.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "upload-service", url = "http://localhost:8004/api/upload")
public interface RemoteUploadService {
	@PostMapping("/{ownerId}")
	Object upload(@PathVariable String ownerId, @RequestParam("files") MultipartFile[] files);

	@DeleteMapping("/filename/{fileName}")
	Object remove(@PathVariable String fileName);

	@DeleteMapping("/{ownerId}")
	Object removeAllByOwner(@PathVariable String ownerId);

	@GetMapping("/{ownerId}")
	Object get(@PathVariable String ownerId);

	@GetMapping("/filename/{fileName}")
	Object getFile(@PathVariable String fileName);
}
