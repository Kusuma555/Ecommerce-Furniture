package com.cg.order.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.order.dto.OrderDto;
import com.cg.order.dto.ResponseObject;
import com.cg.order.entity.Order;
import com.cg.order.entity.OrderStatus;
import com.cg.order.entity.Product;
import com.cg.order.repository.OrderRepo;
import com.cg.order.service.OrderService;
import com.cg.order.service.RemoteProductService;
import com.cg.order.service.RemoteUserService;

@Service
public class OrderServiceImpl implements OrderService {
	private final static int DEFAULT_DELIVERY_DAYS = 7;

	@Autowired
	private OrderRepo repo;
	@Autowired
	private RemoteUserService userService;
	@Autowired
	private RemoteProductService productService;
	@Autowired
	private ModelMapper mapper;

	@Override
	public OrderDto add(OrderDto dto) {
		// validate user
		ResponseObject res = userService.get(dto.getUserId());
		if (res.isError())
			throw new RuntimeException(res.getMessage());

		// validate product
		List<Product> products = dto.getProducts().stream().map((prodDto) -> {
			ResponseObject productRes = productService.get(prodDto.getId());

			if (productRes.isError())
				throw new RuntimeException(res.getMessage());

			Map<String, String> data = (Map) productRes.getData();

			int stock = Integer.parseInt(data.get("stock"));
			if (prodDto.getQuantity() > stock)
				throw new RuntimeException("Not enough stock for " + data.get("title"));

			Double price = Double.parseDouble(data.get("price"));
			prodDto.setPrice(price);

			return mapper.map(dto, Product.class);

		}).collect(Collectors.toList());

		Order order = mapper.map(dto, Order.class);
		order.setProducts(products);

		// generate delivery date
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, DEFAULT_DELIVERY_DAYS);
		LocalDate deliveryDate = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
		order.setDeliveryDate(deliveryDate);

		// send email

		return mapper.map(repo.save(order), OrderDto.class);
	}

	@Override
	public OrderDto updateStatus(Long orderId, OrderStatus status) {
		Order order = repo.findById(orderId).orElseThrow(() -> new RuntimeException("No order found in the records"));
		order.setStatus(status);
		return mapper.map(repo.save(order), OrderDto.class);

	}

	@Override
	public OrderDto get(Long orderId) {
		Order order = repo.findById(orderId).orElseThrow(() -> new RuntimeException("No order found in the records"));
		return mapper.map(order, OrderDto.class);
	}

	@Override
	public List<OrderDto> getByUser(Long userId) {
		List<Order> orders = repo.findByUserId(userId);
		return mapper.map(orders, new TypeToken<List<Order>>() {
		}.getType());
	}

}
