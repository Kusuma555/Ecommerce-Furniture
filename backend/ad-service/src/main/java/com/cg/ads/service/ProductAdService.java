package com.cg.ads.service;

import com.cg.ads.dto.ProductAdDto;

public interface ProductAdService {
	Object get();

	Object get(int page);

	Object get(String query, int page);

	Object get(String by, String order, int page);

	Object get(Long adId);

	Object get(Long userId, int page);

	Object add(ProductAdDto dto);

	Object update(ProductAdDto dto);

	Object accept(Long adId);
	
	Object reject(Long adId);
	
	Object delete(Long productId);
}
