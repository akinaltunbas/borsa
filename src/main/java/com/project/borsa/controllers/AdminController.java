package com.project.borsa.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.borsa.services.RoleService;
import com.project.borsa.services.ShareService;
import com.project.borsa.services.UserService;
import com.project.borsa.dto.UserResponseDto;
import com.project.borsa.dto.UserUpdateRequestDto;
import com.project.borsa.entities.Role;
import com.project.borsa.entities.Share;
import com.project.borsa.entities.User;
import com.project.borsa.exceptions.UserNotFoundException;
import com.project.borsa.dto.RoleCreateRequestDto;
import com.project.borsa.dto.RoleUpdateRequestDto;
import com.project.borsa.dto.ShareCreateRequestDto;
import com.project.borsa.dto.ShareUpdateRequestDto;
import com.project.borsa.dto.UserCreateRequestDto;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private UserService userService;
	private ShareService shareService;
	private RoleService roleService;
	
	
//----------------------------------------------- SHARE HANDLİNG ----------------------------------------------------------------------------------------------//
	
	public AdminController(UserService userService, ShareService shareService, RoleService roleService) {
		this.userService = userService;
		this.shareService = shareService;
		this.roleService = roleService;
	}

	@GetMapping("/listShare")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Share> getAllShares(){
		return shareService.getAllShares();
	}
	
	@PostMapping("/createShare")
	@PreAuthorize("hasRole('ADMIN')")
	public Share craeteOneShare(@RequestBody ShareCreateRequestDto newShareRequest) {
		return shareService.createOneShare(newShareRequest);
	}
	
	@GetMapping("/getShare/{shareId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Share getOneShare(@PathVariable Long shareId) {
		return shareService.getShareById(shareId);
	}
	
	@PutMapping("/updateShare/{shareId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> updateOneShare(@PathVariable Long shareId,@RequestBody ShareUpdateRequestDto updateShareRequest){
		Share share = shareService.updateOneShareById(shareId,updateShareRequest);
		if(share !=null) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	
	@DeleteMapping("/deleteShare/{shareId}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteOneShare(@PathVariable Long shareId) {
		shareService.deleteOneShareById(shareId);
	}
	
	
	//----------------------------------------------- USER HANDLİNG ----------------------------------------------------------------------------------------------//

	@GetMapping("/listUser")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getallUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/createUser")
	@PreAuthorize("hasRole('ADMIN')")
	public User createOneUser(@RequestBody UserCreateRequestDto newUserRequest) {
		return userService.createOneUser(newUserRequest);
	}
	
	@GetMapping("/getUser/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public UserResponseDto getOneUser(@PathVariable Long userId) {
		User user = userService.getOneUserById(userId);
		if(user ==null) {
			throw new UserNotFoundException();
		}
		return new UserResponseDto(user);
		
	}
	
	@PutMapping("/updateUser/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> updateOneUser(@PathVariable Long userId,@RequestBody UserUpdateRequestDto updateUserRequest){
		User user = userService.updateOneUserById(userId,updateUserRequest);
		if(user !=null) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	
	
	@DeleteMapping("/deleteUser/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteOneUser(@PathVariable Long userId) {
		 userService.deleteOneUserById(userId);
	}
	
	//----------------------------------------------- ROLE HANDLİNG ----------------------------------------------------------------------------------------------//
	
	@GetMapping("/listRole")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Role> getAllRoles(){
		return roleService.getAllRoles();
	}
	
	@GetMapping("/getRole/{roleId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Role getOneRole(@PathVariable Long roleId) {
		return roleService.getRoleById(roleId);
	}

	@PostMapping("/createRole")
	@PreAuthorize("hasRole('ADMIN')")
	public Role craeteOneRole(@RequestBody RoleCreateRequestDto newRoleRequest) {
		return roleService.createOneRole(newRoleRequest);
	}
	
	@PutMapping("/updateRole/{roleId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> updateOneRole(@PathVariable Long roleId,@RequestBody RoleUpdateRequestDto updateRoleRequest) {
		Role role = roleService.updateOneRoleById(roleId,updateRoleRequest);
		if(role != null)
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/deleteRole/{roleId}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteOneRole(@PathVariable Long roleId) {
		roleService.deleteOneRoleById(roleId);
	}
}
