package com.project.borsa.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.borsa.services.ShareService;
import com.project.borsa.services.UserService;
import com.project.borsa.dto.UserResponseDto;
import com.project.borsa.dto.UserUpdateRequestDto;
import com.project.borsa.entities.Share;
import com.project.borsa.entities.User;
import com.project.borsa.exceptions.UserNotFoundException;
import com.project.borsa.dto.ShareCreateRequestDto;
import com.project.borsa.dto.ShareUpdateRequestDto;
import com.project.borsa.dto.UserCreateRequestDto;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private UserService userService;
	private ShareService shareService;
	
	public AdminController(UserService userService, ShareService shareService) {
		this.userService = userService;
		this.shareService = shareService;
	}
	
//----------------------------------------------- SHARE HANDLİNG ----------------------------------------------------------------------------------------------//
	
	@GetMapping("/listShare")
	public List<Share> getAllShares(){
		return shareService.getAllShares();
	}
	
	@PostMapping("/createShare")
	public Share craeteOneShare(@RequestBody ShareCreateRequestDto newShareRequest) {
		return shareService.createOneShare(newShareRequest);
	}
	
	@GetMapping("/getShare/{shareId}")
	public Share getOneShare(@PathVariable Long shareId) {
		return shareService.getShareById(shareId);
	}
	
	@PutMapping("/updateShare/{shareId}")
	public ResponseEntity<Void> updateOneShare(@PathVariable Long shareId,@RequestBody ShareUpdateRequestDto updateShareRequest){
		Share share = shareService.updateOneShareById(shareId,updateShareRequest);
		if(share !=null) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	
	@DeleteMapping("/deleteShare/{shareId}")
	public void deleteOneShare(@PathVariable Long shareId) {
		shareService.deleteOneShareById(shareId);
	}
	
	
	//----------------------------------------------- USER HANDLİNG ----------------------------------------------------------------------------------------------//

	@GetMapping("/listUser")
	public List<User> getallUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/createUser")
	public User createOneUser(@RequestBody UserCreateRequestDto newUserRequest) {
		return userService.createOneUser(newUserRequest);
	}
	
	@GetMapping("/getUser/{userId}")
	public UserResponseDto getOneUser(@PathVariable Long userId) {
		User user = userService.getOneUserById(userId);
		if(user ==null) {
			throw new UserNotFoundException();
		}
		return new UserResponseDto(user);
		
	}
	
	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<Void> updateOneUser(@PathVariable Long userId,@RequestBody UserUpdateRequestDto updateUserRequest){
		User user = userService.updateOneUserById(userId,updateUserRequest);
		if(user !=null) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	
	
	@DeleteMapping("/deleteUser/{userId}")
	public void deleteOneUser(@PathVariable Long userId) {
		 userService.deleteOneUserById(userId);
	}
	

}
