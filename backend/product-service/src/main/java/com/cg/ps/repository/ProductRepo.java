package com.cg.ps.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.ps.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

	List<Product> findByTitleContainingIgnoreCase(String title, Pageable pageable);

	List<Product> findAllByOrderByTitleAsc(Pageable pageable);

	List<Product> findAllByOrderByUpdatedAtAsc(Pageable pageable);

	List<Product> findAllByOrderByPriceAsc(Pageable pageable);

	List<Product> findAllByOrderByTitleDesc(Pageable pageable);

	List<Product> findAllByOrderByUpdatedAtDesc(Pageable pageable);

	List<Product> findAllByOrderByPriceDesc(Pageable pageable);
}
