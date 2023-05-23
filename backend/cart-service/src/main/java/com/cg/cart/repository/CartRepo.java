package com.cg.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.cart.entity.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

	Optional<Cart> findByUserId(Long userId);
}
