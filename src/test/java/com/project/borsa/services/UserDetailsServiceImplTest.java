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
		
		//given
		given(userRepository.findByUsername("akito")).willReturn(user);
				
		//when	
		UserDetails savedUser = userDetailsServiceImpl.loadUserByUsername(user.getUsername());
		
		//then	
		assertThat(savedUser).isNotNull();
		
	}
	

	@DisplayName("JUnit test for loadUserById")
	@Test
	public void testLoadUserById() {
		//given
		given(userRepository.findById(1L)).willReturn(Optional.of(user));
		
		//when
		UserDetails savedUser = userDetailsServiceImpl.loadUserById(user.getId());
				
		//then		
		assertThat(savedUser).isNotNull();	
}
}