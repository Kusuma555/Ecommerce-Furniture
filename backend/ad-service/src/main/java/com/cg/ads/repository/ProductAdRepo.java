package com.cg.ads.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.ads.entity.ProductAd;

@Repository
public interface ProductAdRepo extends JpaRepository<ProductAd, Long> {

	List<ProductAd> findByTitleContainingIgnoreCase(String title, Pageable pageable);

	List<ProductAd> findAllByOrderByTitleAsc(Pageable pageable);

	List<ProductAd> findAllByOrderByUpdatedAtAsc(Pageable pageable);

	List<ProductAd> findAllByOrderByPriceAsc(Pageable pageable);

	List<ProductAd> findAllByOrderByTitleDesc(Pageable pageable);

	List<ProductAd> findAllByOrderByUpdatedAtDesc(Pageable pageable);

	List<ProductAd> findAllByOrderByPriceDesc(Pageable pageable);

	List<ProductAd> findByUserId(Long userId, Pageable pageable);
}
