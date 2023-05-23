package com.cg.upload.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UploadInfo {
	private String ownerId;
	private List<String> urls = new ArrayList<>();
}
