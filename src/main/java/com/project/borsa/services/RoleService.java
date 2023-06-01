package com.project.borsa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.borsa.dto.RoleCreateRequestDto;
import com.project.borsa.dto.RoleUpdateRequestDto;
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

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public void deleteOneRoleById(Long roleId) {
		roleRepository.deleteById(roleId);
		
	}

	public Role getRoleById(Long roleId) {
		return roleRepository.findById(roleId).orElse(null);
	}

	public Role updateOneRoleById(Long roleId, RoleUpdateRequestDto updateRoleRequest) {
		Optional<Role> role = roleRepository.findById(roleId);
		if(role.isPresent()) {
			Role foundRole = role.get();
			foundRole.setName(updateRoleRequest.getName());
			roleRepository.save(foundRole);
			return foundRole;
			
		}else
			
		return null;
	}

}
