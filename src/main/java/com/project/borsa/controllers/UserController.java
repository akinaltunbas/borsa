package com.project.borsa.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.borsa.entities.Share;
import com.project.borsa.services.ShareServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private ShareServiceImpl shareService;

	public UserController(ShareServiceImpl shareService) {
		this.shareService = shareService;
	}
	

	@GetMapping("/listShare")
	@PreAuthorize("hasRole('USER')")
	public List<Share> getAllShares(){
		return shareService.getAllShares();
	}
	
	@GetMapping("/myShares")
	@PreAuthorize("hasRole('USER')")
	public List<Share> getAllMyShares(@RequestParam Long userId) {

		return shareService.getAllMyShares(userId);
	}

}
