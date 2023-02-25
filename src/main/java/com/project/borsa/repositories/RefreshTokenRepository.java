package com.project.borsa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.borsa.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	
	RefreshToken findByUserId(Long userId);

}
