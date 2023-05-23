package com.cg.ps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.ps.entity.ProductCategory;

@Repository
public interface CategoryRepo extends JpaRepository<ProductCategory, Long>{
	Optional<ProductCategory> findByName(String name);

}
