package com.project.borsa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.project.borsa.services.RefreshTokenService;
import com.project.borsa.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private AuthenticationManager authenticationManager;
	
	private JwtTokenProvider jwtTokenProvider;
	
	private UserService userService;
	
	private PasswordEncoder passwordEncoder;

	private RefreshTokenService refreshTokenService;
	
	
	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
			UserService userService, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.refreshTokenService = refreshTokenService;
	}



	@PostMapping("/login")
	public AuthResponseDto login(@RequestBody UserLoginRequestDto loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
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
			authResponse.setMessage("Username already in use.");
			return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setName(registerRequest.getName());
		user.setSurname(registerRequest.getSurname());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		userService.createUser(user);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		
		authResponse.setMessage("User successfully registered.");
		authResponse.setAccessToken("Bearer " + jwtToken);
		authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
		authResponse.setUserId(user.getId());
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);		
	}
	
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshRequestDto refreshRequest) {
		AuthResponseDto response = new AuthResponseDto();
		RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
		if(token.getToken().equals(refreshRequest.getRefreshToken()) &&
				!refreshTokenService.isRefreshExpired(token)) {

			User user = token.getUser();
			String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());
			response.setMessage("token successfully refreshed.");
			response.setAccessToken("Bearer " + jwtToken);
			response.setUserId(user.getId());
			return new ResponseEntity<>(response, HttpStatus.OK);		
		} else {
			response.setMessage("refresh token is not valid.");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
	}
	

}
