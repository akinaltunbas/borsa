package com.project.borsa.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.borsa.dto.UserCreateRequestDto;
import com.project.borsa.dto.UserRegisterRequestDto;
import com.project.borsa.dto.UserUpdateRequestDto;
import com.project.borsa.entities.Role;
import com.project.borsa.entities.User;
import com.project.borsa.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleService roleService;
	
	private User user;
	
	private PasswordEncoder bcryptEncoder;
	
	@BeforeEach
	public void setup() {
		

		user = User.builder()
			   .id(1L)
			   .name("akın")
			   .surname("altunbas")
			   .username("akito")
			   .email("akin@hotmail.com")
			   .password("1234")
			  // .role(Role.USER)
			   .build();
		
	}
	
	
	@DisplayName("Junit test for deleteOneUserById method")
	@Test
	public void givenUserId_whenDeleteOneUser_thenNothing() {
		
		//given
		long userId = 1L;
		
		willDoNothing().given(userRepository).deleteById(userId);
		
		//when
		userService.deleteOneUserById(userId);
		
		//then
		verify(userRepository,times(1)).deleteById(userId);
	}
	
	@DisplayName("Junit test for getOneUserById")
	@Test
	public void givenUserId_whenGetUserById_thenReturnUser() {
		
		//given
		given(userRepository.findById(1L)).willReturn(Optional.of(user));
		
		//when
		User savedUser = userService.getOneUserById(user.getId());
		
		//then
		assertThat(savedUser).isNotNull();	
		
	}
	
	@DisplayName("Junit test for createOneUser")
	@Test
	public void givenUser_whenCreateUser_thenReturnUser() {
		
		//given
		UserCreateRequestDto userRequestDto = new UserCreateRequestDto();
		userRequestDto.setName("Test-Name");
		userRequestDto.setSurname("Test-Surname");
		userRequestDto.setUsername("Test-Username");
		userRequestDto.setEmail("Test-Email");
		userRequestDto.setPassword("Test-Password");
	//	userRequestDto.setRole(Role.USER);
		
		//when
		User userMock = Mockito.mock(User.class);
		when(userMock.getId()).thenReturn(1L);
		Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(userMock);
		User savedUser = userService.createOneUser(userRequestDto);
		
		//then
		assertEquals(savedUser.getId(),1L);
		
	}
	
	@DisplayName("Junit test for updateOneUserById method")
	@Test
	public void givenUser_whenUpdateUser_thenReturnUpdateUser() {
		
		//given
		UserUpdateRequestDto updateUserRequest = new UserUpdateRequestDto();
		long userId= 1L;
		given(userRepository.findById(1L)).willReturn(Optional.of(user));
		given (userRepository.save(user)).willReturn(user);
		updateUserRequest.setName("akın");
		updateUserRequest.setSurname("altunbas");
		updateUserRequest.setUsername("akito");
		updateUserRequest.setEmail("akin@hotmail.com");
		updateUserRequest.setPassword("1234");
	//	updateUserRequest.setRole(Role.USER);
		
		//when
		User updateUser = userService.updateOneUserById(userId, updateUserRequest);
		
		//then
		assertThat(updateUser.getName()).isEqualTo("akın");
		assertThat(updateUser.getSurname()).isEqualTo("akito");
		assertThat(updateUser.getUsername()).isEqualTo("akito");
		assertThat(updateUser.getEmail()).isEqualTo("akin@hotmail.com");
		assertThat(updateUser.getPassword()).isEqualTo("1234");
	//	assertThat(updateUser.getRole()).isEqualTo(Role.USER);
				
	}
	
	@DisplayName("Junit test for getAllUsers method")
	@Test
	public void givenUserList_whenGetAllUsers_thenReturnUserList() {
		
		//given
		User user2 = new User(2L,"anıl","yetisgin","anil","anıl@hotmail.com","1234",null);
		given(userRepository.findAll()).willReturn(List.of(user,user2));
		
		//when
		List<User> userList = userService.getAllUsers();
		
		//then
		assertThat(userList).isNotNull();
		assertThat(userList.size()).isEqualTo(2);
	}
	
	@DisplayName("Junit test for getOneUserByUserName method")
	@Test
	public void givenUserName_whenGetUserName_thenReturnUser() {
		
		//given
		given(userRepository.findByUsername("akito")).willReturn(user);
		
		//when
		User savedUser = userService.getOneUserByUserName(user.getUsername());
		
		//then
		assertThat(savedUser).isNotNull();
	}
	
	@DisplayName("Junit test for createUser method")
	@Test
	public void givenUser_whencreateUser_thenReturnUser() {
		
		//given
		given(userRepository.save(user)).willReturn(user);
		
		//when
		User savedUser = userService.createUser(user);
		
		//then
		assertThat(savedUser).isNotNull();
	}
	
	@DisplayName("JUnit test for loadUserByUsername")
	@Test
	public void testLoadUserByUsername() {
		
		//given
		given(userRepository.findByUsername("akito")).willReturn(user);
		given(userRepository.findByUsername(null));
				
		//when	
		UserDetails savedUser = userService.loadUserByUsername(user.getUsername());
		
		//then	
		assertThat(savedUser).isNotNull();
		
	}
	

}
