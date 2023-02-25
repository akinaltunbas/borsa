package com.project.borsa.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.borsa.entities.Share;
import com.project.borsa.services.ShareService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private ShareService shareService;

	public UserController(ShareService shareService) {
		this.shareService = shareService;
	}
	

	@GetMapping("/listShare")
	public List<Share> getAllShares(){
		return shareService.getAllShares();
	}
	
	@GetMapping
	public List<Share> getAllMyShares(@RequestParam Long userId) {

		return shareService.getAllMyShares(userId);
	}

}
