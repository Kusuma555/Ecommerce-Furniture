package com.cg.ps.service;

import com.cg.ps.dto.ProductDto;

public interface ProductService {
	Object get();

	Object get(int page);

	Object get(String query, int page);

	Object get(String by, String order, int page);

	Object get(Long productId);

	Object add(ProductDto dto);

	Object update(ProductDto dto);

	Object delete(Long productId);
}
