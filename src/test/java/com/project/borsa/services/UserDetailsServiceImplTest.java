package com.project.borsa.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.borsa.entities.Role;
import com.project.borsa.entities.User;
import com.project.borsa.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)

public class UserDetailsServiceImplTest {
	
	@InjectMocks
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Mock
	private UserRepository userRepository;
	
	private User user;
	
	@BeforeEach
	public void setup() {
		

		user = User.builder()
			   .id(1L)
			   .name("akito")
			   .surname("altunbas")
			   .username("akito")
			   .email("akin@hotmail.com")
			   .password("1234")
			   .role(Role.USER)
			   .build();
		
	}
	
	
	@DisplayName("JUnit test for loadUserByUsername")
	@Test
	public void testLoadUserByUsername() {
		
		
		
		given(userRepository.findByUsername("akito")).willReturn(user);
				
				
		UserDetails savedUser = userDetailsServiceImpl.loadUserByUsername(user.getUsername());
				
		assertThat(savedUser).isNotNull();
		
	}
	

	@DisplayName("JUnit test for loadUserById")
	@Test
	public void testLoadUserById() {
	
		
		given(userRepository.findById(1L)).willReturn(Optional.of(user));
				
				
		UserDetails savedUser = userDetailsServiceImpl.loadUserById(user.getId());
				
				
		assertThat(savedUser).isNotNull();	
}
}