package com.cg.upload.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.upload.entity.Upload;

@Repository
public interface UploadRepo extends JpaRepository<Upload, Long> {
	List<Upload> findByOwnerId(String ownerId);

	List<Upload> findByFileName(String fileName);

	Integer deleteByOwnerId(String ownerId);
	
	Integer deleteByFileName(String fileName);
}
