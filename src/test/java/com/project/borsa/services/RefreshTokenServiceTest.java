package com.project.borsa.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.borsa.entities.RefreshToken;
import com.project.borsa.entities.User;
import com.project.borsa.repositories.RefreshTokenRepository;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {
	
	@InjectMocks
	private RefreshTokenServiceImpl refreshTokenService;
	
	@Mock
	private RefreshTokenRepository refreshTokenRepository;
	
	private RefreshToken refreshToken;
	
	
	@DisplayName("Junit test for getByUser")
	@Test 
	public void testGetByUser() {
	//given
	RefreshToken tokenMock = Mockito.mock(RefreshToken.class);
	
	//when
	Mockito.when(refreshTokenRepository.findByUserId(ArgumentMatchers.any())).thenReturn(tokenMock);
	
	RefreshToken savedToken = refreshTokenService.getByUser(1L);
	
	//then
	assertThat(savedToken).isNotNull();
				
		
	}
	
	
	
	@DisplayName("Junit test for createRefreshToken")
	@Test
	public void testCreateRefreshToken_NotNull() {
		//given
		User user = new User();
	
		RefreshToken tokenMock = Mockito.mock(RefreshToken.class);

		Mockito.when(refreshTokenRepository.findByUserId(ArgumentMatchers.any())).thenReturn(tokenMock);
		
		//when
		String savedToken = refreshTokenService.createRefreshToken(user);
		
		savedToken= "9d55e4f2-4110-4e07-9428-e6c5fd5eba9c";
		
		//then
		assertThat(savedToken).isNotNull();
		
		
	}
	@DisplayName("Junit test for createRefreshToken")
	@Test
	public void testCreateRefreshToken_Null() {
		//given
		User user = new User();
		
		//when
		String savedToken = refreshTokenService.createRefreshToken(user);
		
		savedToken= null;
		
		//then
		assertThat(savedToken).isNull();
	}
		

}
