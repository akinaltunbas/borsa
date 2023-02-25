package com.project.borsa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.borsa.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
