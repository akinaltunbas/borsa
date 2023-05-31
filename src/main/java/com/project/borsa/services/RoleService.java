package com.project.borsa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.borsa.dto.RoleCreateRequestDto;
import com.project.borsa.entities.Role;
import com.project.borsa.repositories.RoleRepository;

@Service(value = "roleService")
public class RoleService {
	

	private RoleRepository roleRepository;
	
	
	
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}



	public Role findByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        return role;
    }
	
	public Role createOneRole(RoleCreateRequestDto newRoleRequest) {
		Role toSaveRole = new Role();
		toSaveRole.setName(newRoleRequest.getName());
		return roleRepository.save(toSaveRole);
		
		
	}

}
