package com.cg.ps.service;

import com.cg.ps.dto.ProductCategoryDto;

public interface ProductCategoryService {
	Object get();
	
	Object get(Long categoryId);
	
	Object add(ProductCategoryDto dto);
	
	Object update(ProductCategoryDto dto);
	
	Object delete(Long categoryId);
}
