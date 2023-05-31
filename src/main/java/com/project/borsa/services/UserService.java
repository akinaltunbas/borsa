package com.project.borsa.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.borsa.dto.UserCreateRequestDto;
import com.project.borsa.dto.UserRegisterRequestDto;
import com.project.borsa.dto.UserUpdateRequestDto;
import com.project.borsa.entities.Role;
import com.project.borsa.entities.User;
import com.project.borsa.repositories.UserRepository;


@Service(value = "userService")
public class UserService implements UserDetailsService {
	

	private UserRepository userRepository;

	private RoleService roleService;

	private PasswordEncoder bcryptEncoder;
	
	

	@Autowired
	public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder bcryptEncoder) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.bcryptEncoder = bcryptEncoder;
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
	//	toSaveUser.setRole(newUserRequest.getRole());
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
	//		toUpdate.setRole(updateUserRequest.getRole());
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
	
	public User save(UserRegisterRequestDto user) {

        User nUser = user.getUserRegisterRequestDto();
        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(nUser.getEmail().split("@")[1].equals("admin.edu")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userRepository.save(nUser);
    }
	
	
	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }
	
	
}
