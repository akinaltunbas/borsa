package com.project.borsa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.borsa.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findRoleByName(String name);

}
