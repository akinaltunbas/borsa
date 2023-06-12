package com.project.borsa.services;

import java.util.List;

import com.project.borsa.dto.RoleCreateRequestDto;
import com.project.borsa.dto.RoleUpdateRequestDto;
import com.project.borsa.entities.Role;

public interface RoleService {
	
	public Role findByName(String name);
	
	public Role createOneRole(RoleCreateRequestDto newRoleRequest);
	
	public List<Role> getAllRoles();
	
	public void deleteOneRoleById(Long roleId);
	
	public Role getRoleById(Long roleId);
	
	public Role updateOneRoleById(Long roleId, RoleUpdateRequestDto updateRoleRequest) ;
	
	
	

}
