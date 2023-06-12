package com.project.borsa.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.project.borsa.dto.UserCreateRequestDto;
import com.project.borsa.dto.UserRegisterRequestDto;
import com.project.borsa.dto.UserUpdateRequestDto;
import com.project.borsa.entities.User;

public interface UserService {
	
	public List<User> getAllUsers();
	
	public User createOneUser(UserCreateRequestDto newUserRequest);
	
	public User getOneUserById(Long userId);
	
	public User updateOneUserById(Long userId, UserUpdateRequestDto updateUserRequest);
	
	public void deleteOneUserById(Long userId);
	
	public User getOneUserByUserName(String userName);
	
	public User createUser(User user);
	
	public User save(UserRegisterRequestDto user);
	
	
	
	
}
