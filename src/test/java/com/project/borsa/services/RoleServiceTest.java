package com.project.borsa.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.borsa.dto.RoleCreateRequestDto;
import com.project.borsa.dto.RoleUpdateRequestDto;
import com.project.borsa.entities.Role;

import com.project.borsa.repositories.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
	
	@InjectMocks
	private RoleServiceImpl roleService;
	
	@Mock
	private RoleRepository roleRepository;
	
	private Role role;
	@BeforeEach
	public void setup() {
		Role role = new Role(1L,"ADMIN");
	}
	
	@DisplayName("junit test for deleteOneRoleById method")
	@Test
	public void givenRoleId_whenDeleteOneRole_thenNothing() {
		
		//given
		long roleId = 1L;
		
		willDoNothing().given(roleRepository).deleteById(roleId);
		
		//when
		roleService.deleteOneRoleById(roleId);
		
		//then
		verify(roleRepository, times(1)).deleteById(roleId);
		
	}
	
	@DisplayName("Jnit test for getAllRoles method")
	@Test
	public void givenRoleList_whenGetAllRoles_thenReturnRoleList() {
		
		//given
		Role role1 = new Role("ADMIN");
		Role role2 = new Role("USER");
		given(roleRepository.findAll()).willReturn(List.of(role1,role2));
		
		//when
		List<Role> roleList = roleService.getAllRoles();
		
		//then
		assertThat(roleList).isNotNull();
		assertThat(roleList.size()).isEqualTo(2);
	}
	
	@DisplayName("JUnit test for updateOneRoleById method")
	@Test
	public void givenRole_whenUpdateRole_thenReturnUpdateRole() {
		//given
		Role role = new Role("ADMIN");
		RoleUpdateRequestDto updateRoleRequest = new RoleUpdateRequestDto();
		Long roleId = 1L;
		given(roleRepository.findById(roleId)).willReturn(Optional.of(role));
		given(roleRepository.save(role)).willReturn(role);
		
		//when
		Role updateRole = roleService.updateOneRoleById(roleId, updateRoleRequest);
		
		//then
		assertThat(updateRoleRequest.getName()).isEqualTo("ADMIN");
	}
	
	@DisplayName("JUnit test for createOneRole")
	@Test
	public void givenRole_whenCreateRole_thenReturnRole() {
		
		//given
		RoleCreateRequestDto roleRequestDto = new RoleCreateRequestDto();
		roleRequestDto.setName("Test-Name");
		
		
		//when
		Role roleMock = Mockito.mock(Role.class);
		when(roleMock.getId()).thenReturn(1L);
		Mockito.when(roleRepository.save(ArgumentMatchers.any(Role.class))).thenReturn(roleMock);
		
		Role saveRole = roleService.createOneRole(roleRequestDto);

		//then
		assertEquals(saveRole.getId(),1L);
	}
	
	@DisplayName("JUnit test for getRoleById")
	@Test
	public void givenRoleId_whenGetRoleById_thenReturnRole() {
		
		// given
		Role role = new Role(1L,"ADMIN");
		given(roleRepository.findById(1L)).willReturn(Optional.of(role));
		
		// when
		Role savedRole = roleService.getRoleById(role.getId());
		
		//then
		assertThat(savedRole).isNotNull();
	}
	

	@DisplayName("JUnit test for findByName")
	@Test
	public void givenRoleName_whenGetRoleName_thenReturnRole() {
		//given
		given(roleRepository.findRoleByName("ADMIN")).willReturn(role);
	
		
		//when
		Role savedRole = roleService.findByName(role.getName());
		
		//then
		assertThat(savedRole).isNotNull();
	}
		
	
}
