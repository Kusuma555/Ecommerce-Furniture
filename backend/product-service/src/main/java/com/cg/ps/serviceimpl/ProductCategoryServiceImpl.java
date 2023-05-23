package com.cg.ps.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ps.dto.ProductCategoryDto;
import com.cg.ps.entity.ProductCategory;
import com.cg.ps.repository.CategoryRepo;
import com.cg.ps.service.ProductCategoryService;
import org.modelmapper.TypeToken;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private CategoryRepo repo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<ProductCategoryDto> get() {
		return mapper.map(repo.findAll(), new TypeToken<List<ProductCategory>>() {
		}.getType());
	}

	@Override
	public ProductCategoryDto get(Long categoryId) {
		ProductCategory cat = repo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
		return mapper.map(cat, ProductCategoryDto.class);
	}

	@Override
	public ProductCategoryDto add(ProductCategoryDto dto) {
		if (repo.findByName(dto.getName()).isPresent())
			throw new RuntimeException("Category with same name already exists");
		
		ProductCategory category = mapper.map(dto, ProductCategory.class);
		return mapper.map(repo.save(category), ProductCategoryDto.class);
	}

	@Override
	public ProductCategoryDto update(ProductCategoryDto dto) {
		ProductCategory cat = repo.findById(dto.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category not found"));
		cat = mapper.map(dto, ProductCategory.class);
		cat.setCategoryId(dto.getCategoryId());
		return mapper.map(repo.save(cat), ProductCategoryDto.class);
	}

	@Override
	public ProductCategoryDto delete(Long categoryId) {
		Optional<ProductCategory> cat = repo.findById(categoryId);
		if (cat.isPresent()) {
			repo.deleteById(categoryId);
			return mapper.map(cat, ProductCategoryDto.class);
		}
		return null;
	}

}
