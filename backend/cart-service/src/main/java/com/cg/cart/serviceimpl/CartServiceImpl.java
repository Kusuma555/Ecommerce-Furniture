package com.cg.cart.serviceimpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.cart.dto.CartDto;
import com.cg.cart.dto.ProductDto;
import com.cg.cart.dto.ResponseObject;
import com.cg.cart.entity.Cart;
import com.cg.cart.entity.Product;
import com.cg.cart.repository.CartRepo;
import com.cg.cart.service.CartService;
import com.cg.cart.service.RemoteProductService;
import com.cg.cart.service.RemoteUserService;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepo repo;
	@Autowired
	private RemoteUserService userService;
	@Autowired
	private RemoteProductService productService;
	@Autowired
	private ModelMapper mapper;

	@Override
	public CartDto update(Long userId, ProductDto dto) {
		// validate user
		ResponseObject res = userService.get(userId);
		if (res.isError())
			throw new RuntimeException(res.getMessage());

		// validate product
		res = productService.get(dto.getProductId());
		if (res.isError())
			throw new RuntimeException(res.getMessage());

		// check for cart
		Optional<Cart> cartOptional = repo.findByUserId(userId);
		Cart cart = null;
		if (cartOptional.isEmpty()) {
			cart = new Cart();
			cart.setUserId(userId);
			cart.getProducts().add(mapper.map(dto, Product.class));
		} else {
			cart = cartOptional.get();
			List<Product> products = cart.getProducts().stream().map((prod) -> {
				if (prod.getProductId().equals(dto.getProductId())) {
					prod.setQuantity(dto.getQuantity());
				}
				return prod;
			}).collect(Collectors.toList());
			cart.setProducts(products);
		}

		// update cart

		return mapper.map(repo.save(cart), CartDto.class);
	}

	@Override
	public CartDto remove(Long userId, Long productId) {
		Optional<Cart> cartOptional = repo.findByUserId(userId);
		Cart cart = null;
		if (cartOptional.isPresent()) {
			cart = cartOptional.get();
			List<Product> products = cart.getProducts().stream()
					.filter((prod) -> !prod.getProductId().equals(productId)).collect(Collectors.toList());
			cart.setProducts(products);
		}
		return mapper.map(repo.save(cart), CartDto.class);
	}

	@Override
	public Object get(Long userId) {
		ResponseObject res = userService.get(userId);
		if (res.isError())
			throw new RuntimeException(res.getMessage());

		Optional<Cart> cartOptional = repo.findByUserId(userId);
		Cart cart = null;
		if (cartOptional.isEmpty()) {
			cart = new Cart();
			cart.setUserId(userId);
			cart = repo.save(cart);
		} else
			cart = cartOptional.get();

		return mapper.map(cart, CartDto.class);
	}

}
