package com.project.borsa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.borsa.dto.UserCreateRequestDto;
import com.project.borsa.dto.UserUpdateRequestDto;
import com.project.borsa.entities.User;
import com.project.borsa.repositories.UserRepository;


@Service
public class UserService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {

		this.userRepository = userRepository;
	}


	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User createOneUser(UserCreateRequestDto newUserRequest) {
		User toSaveUser = new User();
		toSaveUser.setName(newUserRequest.getName());
		toSaveUser.setSurname(newUserRequest.getSurname());
		toSaveUser.setUsername(newUserRequest.getUsername());
		toSaveUser.setEmail(newUserRequest.getEmail());
		toSaveUser.setPassword(newUserRequest.getPassword());
		toSaveUser.setRole(newUserRequest.getRole());
		return userRepository.save(toSaveUser);
	
	}


	public User getOneUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}


	public User updateOneUserById(Long userId, UserUpdateRequestDto updateUserRequest) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			User toUpdate = user.get();
			toUpdate.setName(updateUserRequest.getName());
			toUpdate.setSurname(updateUserRequest.getUsername());
			toUpdate.setUsername(updateUserRequest.getUsername());
			toUpdate.setEmail(updateUserRequest.getEmail());
			toUpdate.setPassword(updateUserRequest.getPassword());
			toUpdate.setRole(updateUserRequest.getRole());
			userRepository.save(toUpdate);
			return toUpdate;
		}
		return null;
	}


	public void deleteOneUserById(Long userId) {
		userRepository.deleteById(userId);
		
	}
	
	public User getOneUserByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}


	public User createUser(User user) {
		return userRepository.save(user);
		
	}
}
