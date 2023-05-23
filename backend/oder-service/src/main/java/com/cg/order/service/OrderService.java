package com.cg.order.service;

import com.cg.order.dto.OrderDto;
import com.cg.order.entity.OrderStatus;

public interface OrderService {
	Object get(Long orderId);

	Object getByUser(Long userId);

	Object add(OrderDto dto);

	Object updateStatus(Long orderId, OrderStatus status);
}
