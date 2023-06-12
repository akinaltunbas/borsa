package com.project.borsa.dto;

import com.project.borsa.entities.Role;

public class RoleCreateRequestDto {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public void mapRoleCreateRequestDtoToRole(Role role) {
		role.setName(this.getName());
	}
}
