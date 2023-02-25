package com.project.borsa.dto;

import com.project.borsa.entities.Role;
import com.project.borsa.entities.User;

public class UserResponseDto {
	
	private Long id;
	private String name;
	private String surname;
	private String username;
	private String email;
	private String password;
	private Role role;
	
	public UserResponseDto(User entity) {
		this.id =  entity.getId();
		this.name = entity.getName();
		this.surname = entity.getSurname();
		this.username = entity.getUsername();
		this.email = entity.getEmail();
		this.password = entity.getPassword();
		this.role = entity.getRole();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	

}
