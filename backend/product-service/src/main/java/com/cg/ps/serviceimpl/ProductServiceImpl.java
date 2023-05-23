package com.cg.ps.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cg.ps.dto.ProductCategoryDto;
import com.cg.ps.dto.ProductDto;
import com.cg.ps.entity.Product;
import com.cg.ps.entity.ProductCategory;
import com.cg.ps.repository.ProductRepo;
import com.cg.ps.service.ProductCategoryService;
import com.cg.ps.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	private final static String BY_NAME = "name";
	private final static String BY_DATE = "date";
	private final static String BY_PRICE = "price";
	private final static int ITEM_PER_PAGE = 20;

	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private ProductCategoryService categoryService;
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<ProductDto> get() {
		return mapper.map(productRepo.findAll(), new TypeToken<List<ProductDto>>() {
		}.getType());
	}

	@Override
	public List<ProductDto> get(int page) {
		Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
		return mapper.map(productRepo.findAll(pageable), new TypeToken<List<ProductDto>>() {
		}.getType());
	}

	@Override
	public List<ProductDto> get(String by, String order, int page) {
		List<Product> products = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
		boolean asc = order.equalsIgnoreCase("asc");
		switch (by) {
		case BY_NAME:
			products = asc ? productRepo.findAllByOrderByTitleAsc(pageable)
					: productRepo.findAllByOrderByTitleDesc(pageable);
			break;

		case BY_DATE:
			products = asc ? productRepo.findAllByOrderByUpdatedAtAsc(pageable)
					: productRepo.findAllByOrderByUpdatedAtDesc(pageable);
			break;

		case BY_PRICE:
			products = asc ? productRepo.findAllByOrderByPriceAsc(pageable)
					: productRepo.findAllByOrderByPriceDesc(pageable);
			break;
		}
		return mapper.map(products, new TypeToken<List<ProductDto>>() {
		}.getType());
	}

	@Override
	public List<ProductDto> get(String query, int page) {
		Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
		return mapper.map(productRepo.findByTitleContainingIgnoreCase(query, pageable),
				new TypeToken<List<ProductDto>>() {
				}.getType());
	}

	@Override
	public ProductDto get(Long productId) {
		Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public ProductDto add(ProductDto dto) {
		return mapper.map(productRepo.save(mapper.map(dto, Product.class)), ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto dto) {
		Product product = productRepo.findById(dto.getProductId())
				.orElseThrow(() -> new RuntimeException("Product not found"));

		if (dto.getTitle() != null && !product.getTitle().equals(dto.getTitle()))
			product.setTitle(dto.getTitle());

		if (dto.getPrice() != product.getPrice())
			product.setPrice(dto.getPrice());

		if (dto.getStock() != product.getStock())
			product.setStock(dto.getStock());
		
		if (dto.getDescription() != null && !product.getDescription().equals(dto.getDescription()))
			product.setDescription(dto.getDescription());

		if (dto.getCategoryId() != null && !product.getCategoryId().equals(dto.getCategoryId())) {
			ProductCategoryDto cat = (ProductCategoryDto) categoryService.get(dto.getCategoryId());
			product.setCategoryId(cat.getCategoryId());
		}

		return mapper.map(productRepo.save(product), ProductDto.class);
	}

	@Override
	public Object delete(Long productId) {
		Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
		productRepo.deleteById(productId);
		return mapper.map(product, ProductDto.class);
	}

}
