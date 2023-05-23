package com.cg.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.authservice.entity.UserToken;

@Repository
public interface UserTokenRepo extends JpaRepository<UserToken, Long> {
	Optional<UserToken> findByToken(String token);

	Optional<UserToken> findByUserId(Long userId);
	
	Integer deleteByUserId(Long userId);
	
	Integer deleteByToken(String token);
}
