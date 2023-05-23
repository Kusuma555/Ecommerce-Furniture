package com.cg.upload.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
	private static final String ROOT_DIR = "uploads/";

	String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) {
		Path uploadPath = Paths.get(ROOT_DIR + uploadDir);

		try {
			if (!Files.exists(uploadPath))
				Files.createDirectories(uploadPath);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new RuntimeException("Could not save image file: " + fileName, ioe);
		}

		return ROOT_DIR + uploadDir + "/" + fileName;

	}

	public String prepareImage(MultipartFile image, String uploadDir) {
		String name = image.getOriginalFilename();

		if (name != null)
			name = StringUtils.cleanPath(name);

		StringBuilder sb = new StringBuilder(name);
		String ext = sb.substring(sb.lastIndexOf(".") + 1);

		if (!validateImageExtension(ext))
			throw new RuntimeException("Invalid Image file");
		name = new SimpleDateFormat("yyyyMMddHHmmssSS'." + ext + "'").format(new Date());
		return saveFile(uploadDir, name, image);

	}

	public boolean deleteDirectory(String ownerId) {
		Path dir = Paths.get(ROOT_DIR + ownerId);

		try {
			return FileSystemUtils.deleteRecursively(dir);
		} catch (IOException e) {
			return false;
		}
	}

	public boolean deleteImage(String path) {
		if (path == null)
			return false;
		try {
			return Files.deleteIfExists(Paths.get(path));
		} catch (IOException e) {
			return false;
		}
	}

	boolean validateImageExtension(String ext) {
		String regex = "(jpe?g|png|gif|bmp)";

		return validatePattern(regex, ext);
	}

	boolean validatePattern(String regex, String data) {
		return Pattern.compile(regex).matcher(data).matches();
	}
}