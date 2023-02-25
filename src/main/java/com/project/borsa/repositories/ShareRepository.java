package com.project.borsa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.borsa.entities.Share;

public interface ShareRepository extends JpaRepository<Share, Long> {
	
	List<Share> findByUserId(Long userId);
}
