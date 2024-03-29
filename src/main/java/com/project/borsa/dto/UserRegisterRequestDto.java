package com.project.borsa.dto;

import java.util.Set;

import com.project.borsa.entities.Role;
import com.project.borsa.entities.User;

public class UserRegisterRequestDto {
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private String username;

	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
	public User getUserRegisterRequestDto() {
		User user = new User();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setUsername(username);
		
		return user;
	}

}
