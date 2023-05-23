package com.cg.ads.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cg.ads.dto.ProductAdDto;
import com.cg.ads.dto.ResponseObject;
import com.cg.ads.entity.ProductAd;
import com.cg.ads.repository.ProductAdRepo;
import com.cg.ads.service.ProductAdService;
import com.cg.ads.service.RemoteProductCategoryService;
import com.cg.ads.service.RemoteUserService;

@Service
public class ProductAdServiceImpl implements ProductAdService {
	private final static String BY_NAME = "name";
	private final static String BY_DATE = "date";
	private final static String BY_PRICE = "price";
	private final static int ITEM_PER_PAGE = 20;

	@Autowired
	private ProductAdRepo repo;
	@Autowired
	private RemoteUserService userService;
	@Autowired
	private RemoteProductCategoryService categoryService;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<ProductAdDto> get() {
		return mapper.map(repo.findAll(), new TypeToken<List<ProductAdDto>>() {
		}.getType());
	}

	@Override
	public List<ProductAdDto> get(int page) {
		Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
		return mapper.map(repo.findAll(pageable), new TypeToken<List<ProductAdDto>>() {
		}.getType());
	}

	@Override
	public List<ProductAdDto> get(String by, String order, int page) {
		List<ProductAd> ads = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
		boolean asc = order.equalsIgnoreCase("asc");
		switch (by) {
		case BY_NAME:
			ads = asc ? repo.findAllByOrderByTitleAsc(pageable) : repo.findAllByOrderByTitleDesc(pageable);
			break;

		case BY_DATE:
			ads = asc ? repo.findAllByOrderByUpdatedAtAsc(pageable) : repo.findAllByOrderByUpdatedAtDesc(pageable);
			break;

		case BY_PRICE:
			ads = asc ? repo.findAllByOrderByPriceAsc(pageable) : repo.findAllByOrderByPriceDesc(pageable);
			break;
		}
		return mapper.map(ads, new TypeToken<List<ProductAdDto>>() {
		}.getType());
	}

	@Override
	public List<ProductAdDto> get(String query, int page) {
		Pageable pageable = PageRequest.of(page, ITEM_PER_PAGE);
		return mapper.map(repo.findByTitleContainingIgnoreCase(query, pageable), new TypeToken<List<ProductAdDto>>() {
		}.getType());
	}

	@Override
	public ProductAdDto get(Long adId) {
		ProductAd product = repo.findById(adId).orElseThrow(() -> new RuntimeException("Ad not found"));
		return mapper.map(product, ProductAdDto.class);
	}

	@Override
	public ProductAdDto add(ProductAdDto dto) {
		ResponseObject res = userService.get(dto.getUserId());
		if (res.isError())
			throw new RuntimeException(res.getMessage());

		res = categoryService.get(dto.getCategoryId());
		if (res.isError())
			throw new RuntimeException(res.getMessage());

		return mapper.map(repo.save(mapper.map(dto, ProductAd.class)), ProductAdDto.class);
	}

	@Override
	public ProductAdDto update(ProductAdDto dto) {
		ProductAd ad = repo.findById(dto.getAdId()).orElseThrow(() -> new RuntimeException("Ad not found"));

		if (dto.getTitle() != null && !ad.getTitle().equals(dto.getTitle()))
			ad.setTitle(dto.getTitle());

		if (dto.getDescription() != null && !ad.getDescription().equals(dto.getDescription()))
			ad.setDescription(dto.getDescription());

		if (dto.getRemarks() != null && !ad.getRemarks().equals(dto.getRemarks()))
			ad.setRemarks(dto.getRemarks());

		if (dto.getPrice() != ad.getPrice())
			ad.setPrice(dto.getPrice());

		if (dto.getQuantity() != ad.getQuantity())
			ad.setQuantity(dto.getQuantity());

		if (dto.getCategoryId() != null && !ad.getCategoryId().equals(dto.getCategoryId())) {
			ResponseObject res = categoryService.get(dto.getCategoryId());
			if (res.isError())
				throw new RuntimeException(res.getMessage());
			ad.setCategoryId(dto.getCategoryId());
		}

		return mapper.map(repo.save(ad), ProductAdDto.class);
	}

	@Override
	public ProductAdDto delete(Long productId) {
		ProductAd ad = repo.findById(productId).orElseThrow(() -> new RuntimeException("Ad not found"));
		repo.deleteById(productId);
		return mapper.map(ad, ProductAdDto.class);
	}

	@Override
	public List<ProductAdDto> get(Long userId, int page) {
		return mapper.map(repo.findByUserId(userId, PageRequest.of(page, ITEM_PER_PAGE)),
				new TypeToken<List<ProductAdDto>>() {
				}.getType());
	}

	@Override
	public ProductAdDto accept(Long adId) {
		ProductAd ad = repo.findById(adId).orElseThrow(() -> new RuntimeException("Ad not found"));
		ad.setAccepted(true);
		return mapper.map(ad, ProductAdDto.class);
	}

	@Override
	public ProductAdDto reject(Long adId) {
		ProductAd ad = repo.findById(adId).orElseThrow(() -> new RuntimeException("Ad not found"));
		ad.setAccepted(false);
		return mapper.map(ad, ProductAdDto.class);
	}

}
