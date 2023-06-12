package com.project.borsa.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.borsa.dto.AuthResponseDto;
import com.project.borsa.dto.RefreshRequestDto;
import com.project.borsa.dto.UserRegisterRequestDto;
import com.project.borsa.entities.RefreshToken;
import com.project.borsa.entities.User;
import com.project.borsa.dto.UserLoginRequestDto;
import com.project.borsa.security.JwtTokenProvider;
import com.project.borsa.services.RefreshTokenServiceImpl;
import com.project.borsa.services.UserServiceImpl;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	
	
	private AuthenticationManager authenticationManager;
	
	private JwtTokenProvider jwtTokenUtil;
	
	private UserServiceImpl userService;
	 
	private RefreshTokenServiceImpl refreshTokenService;
	 
	 
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenUtil,
			UserServiceImpl userService, RefreshTokenServiceImpl refreshTokenService) {
	
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService = userService;
		this.refreshTokenService = refreshTokenService;
	}

	@PostMapping("/login")
	public AuthResponseDto login(@RequestBody UserLoginRequestDto loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenUtil.generateToken(auth);
		User user = userService.getOneUserByUserName(loginRequest.getUsername());
		AuthResponseDto authResponse = new AuthResponseDto();
		authResponse.setAccessToken("Bearer " + jwtToken);
		authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
		authResponse.setUserId(user.getId());
		return authResponse;
	} 
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponseDto> register(@RequestBody UserRegisterRequestDto registerRequest) {
		AuthResponseDto authResponse = new AuthResponseDto();
		if(userService.getOneUserByUserName(registerRequest.getUsername()) != null) {

			return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setName(registerRequest.getName());
		user.setSurname(registerRequest.getSurname());
		user.setPassword((registerRequest.getPassword()));
		userService.save(registerRequest);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenUtil.generateToken(auth);
	
		authResponse.setAccessToken("Bearer " + jwtToken);
		authResponse.setUserId(user.getId());
		authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);		
	}
	
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshRequestDto refreshRequest) {
		AuthResponseDto response = new AuthResponseDto();
		RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
		if(token.getToken().equals(refreshRequest.getRefreshToken()) &&
				!refreshTokenService.isRefreshExpired(token)) {

			User user = token.getUser();
			String jwtToken = jwtTokenUtil.generateJwtTokenByUserId(user.getId());
			response.setAccessToken("Bearer " + jwtToken);
			response.setUserId(user.getId());
			return new ResponseEntity<>(response, HttpStatus.OK);		
		} else {
			
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	

}
