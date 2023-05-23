package com.cg.upload.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cg.upload.dto.UploadInfo;
import com.cg.upload.entity.Upload;
import com.cg.upload.repository.UploadRepo;
import com.cg.upload.service.UploadService;
import com.cg.upload.util.UploadUtil;

import jakarta.transaction.Transactional;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private UploadUtil uploadUtil;
	@Autowired
	private UploadRepo repo;

	@Override
	public UploadInfo upload(String ownerId, MultipartFile[] files) {
		UploadInfo info = new UploadInfo();
		info.setOwnerId(ownerId);

		for (MultipartFile file : files) {
			StringBuilder path = new StringBuilder("http://localhost:8004/api/");
			path.append(uploadUtil.prepareImage(file, ownerId));
			String fileName = path.substring(path.lastIndexOf("/") + 1);
			info.getUrls().add(path.toString());

			Upload upload = new Upload();
			upload.setFileName(fileName);
			upload.setOwnerId(ownerId);
			upload.setPath(path.toString());

			repo.save(upload);
		}

		return info;
	}

	@Transactional
	@Override
	public Boolean remove(String fileName) {
		boolean success = uploadUtil.deleteImage(fileName);
		if (success)
			repo.deleteByFileName(fileName);

		return success;
	}

	@Transactional
	@Override
	public Boolean removeAllByOwner(String ownerId) {
		boolean success = uploadUtil.deleteDirectory(ownerId);
		if (success)
			repo.deleteByOwnerId(ownerId);

		return success;
	}

	@Override
	public UploadInfo get(String ownerId) {
		List<Upload> uploads = repo.findByOwnerId(ownerId);

		if (uploads == null || uploads.size() == 0)
			throw new RuntimeException("No images found");

		List<String> urls = uploads.stream().map(upload -> upload.getPath()).collect(Collectors.toList());

		UploadInfo uploadInfo = new UploadInfo();
		uploadInfo.setOwnerId(ownerId);
		uploadInfo.setUrls(urls);
		return uploadInfo;
	}

	@Override
	public UploadInfo getFile(String fileName) {
		List<Upload> uploads = repo.findByFileName(fileName);

		if (uploads == null || uploads.size() == 0)
			throw new RuntimeException("No images found");

		List<String> urls = uploads.stream().map(upload -> upload.getPath()).collect(Collectors.toList());

		UploadInfo uploadInfo = new UploadInfo();
		uploadInfo.setOwnerId(uploads.get(0).getOwnerId());
		uploadInfo.setUrls(urls);
		return uploadInfo;
	}

}
