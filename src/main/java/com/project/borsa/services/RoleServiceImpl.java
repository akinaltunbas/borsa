package com.project.borsa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.borsa.dto.RoleCreateRequestDto;
import com.project.borsa.dto.RoleUpdateRequestDto;
import com.project.borsa.entities.Role;
import com.project.borsa.entities.Share;
import com.project.borsa.repositories.RoleRepository;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
	
	private RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@Override
	public Role findByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        return role;
    }
	
	@Override
	public Role createOneRole(RoleCreateRequestDto newRoleRequest) {
		Role role = new Role();
		newRoleRequest.mapRoleCreateRequestDtoToRole(role);
		return roleRepository.save(role);
			
	}
	
	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public void deleteOneRoleById(Long roleId) {
		roleRepository.deleteById(roleId);
		
	}
	
	@Override
	public Role getRoleById(Long roleId) {
		return roleRepository.findById(roleId).orElse(null);
	}
	
	@Override
	public Role updateOneRoleById(Long roleId, RoleUpdateRequestDto updateRoleRequest) {
		Optional<Role> role = roleRepository.findById(roleId);
		if(role.isPresent()) {
			Role role1 = role.get();
			updateRoleRequest.mapRoleUpdateRequestDtoToRole(role1);
			roleRepository.save(role1);
			return role1;
			
		}else
			
		return null;
	}

}
