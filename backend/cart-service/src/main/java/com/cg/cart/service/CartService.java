package com.cg.cart.service;

import com.cg.cart.dto.ProductDto;

public interface CartService {
	Object get(Long userId);
	
	Object update(Long userId, ProductDto dto);

	Object remove(Long userId, Long productId);
}
