package com.cg.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	Object upload(String ownerId, MultipartFile[] files);

	Object remove(String fileName);

	Object removeAllByOwner(String ownerId);
	
	Object get(String ownerId);
	
	Object getFile(String fileName);
}
