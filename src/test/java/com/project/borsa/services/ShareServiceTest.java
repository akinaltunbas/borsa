package com.project.borsa.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import com.project.borsa.dto.ShareCreateRequestDto;
import com.project.borsa.dto.ShareUpdateRequestDto;
import com.project.borsa.entities.Share;
import com.project.borsa.repositories.ShareRepository;


@ExtendWith(MockitoExtension.class)
public class ShareServiceTest {
	
	@InjectMocks
	private ShareService shareService;
	
	@Mock
	private ShareRepository shareRepository;  
	
	private Share share;
	
 
	@BeforeEach
	public void setup() {
		
		// shareRepository = Mockito.mock(EmployeeRepository.class);
		//shareService = new ShareService(shareRepository);	
		share = Share.builder()
				.id(1L)
				.code("AAA")
				.name("aaa")
				.price(100.00)
				.build();
		
	}
	
	
	@DisplayName("jUnit test for deleteOneShareById method")
	@Test
	public void givenShareId_whenDeleteOneShare_thenNothing() {
		
		//given 
		long shareId = 1L;
		
		willDoNothing().given(shareRepository).deleteById(shareId);
		
		// when 
		shareService.deleteOneShareById(shareId);
		
		// then 
		verify(shareRepository, times(1)).deleteById(shareId);
	}
	
	@DisplayName("JUnit test for getAllShares method")
	@Test
	public void givenShareList_whenGetAllShares_thenReturnShareList() {
		
		//given 
		Share share1 = Share.builder()
				.id(2L)
				.code("THY")
				.name("Türk Hava Yolları")
				.price(100.00)
				.build();
		given(shareRepository.findAll()).willReturn(List.of(share,share1));
		
		
		//when
		List<Share> shareList = shareService.getAllShares();
		
		//then
		assertThat(shareList).isNotNull();
		assertThat(shareList.size()).isEqualTo(2);
	}
	
	@DisplayName("JUnit test for getShareById")
	@Test
	public void givenShareId_whenGetShareById_thenReturnShare() {
		
		// given
		given(shareRepository.findById(1L)).willReturn(Optional.of(share));
		
		// when
		Share savedShare = shareService.getShareById(share.getId());
		
		//then
		assertThat(savedShare).isNotNull();
	}
	
	@DisplayName("JUnit test for updateOneShareById method")
	@Test
	public void givenShare_whenUpdateShare_thenReturnUpdateShare() {
		
		//given
		ShareUpdateRequestDto updateShareRequest = new ShareUpdateRequestDto();
		long shareId = 1L;
		given(shareRepository.findById(1L)).willReturn(Optional.of(share));
		given(shareRepository.save(share)).willReturn(share);
		updateShareRequest.setCode("AAA");
		updateShareRequest.setName("aaa");
		updateShareRequest.setPrice(100.00);
		
		//when 
		Share updateShare = shareService.updateOneShareById(shareId, updateShareRequest);
		
		// then -verify the output
		assertThat(updateShare.getCode()).isEqualTo("AAA");
		assertThat(updateShare.getName()).isEqualTo("aaa");
		assertThat(updateShare.getPrice()).isEqualTo(100);
	}
	
	@DisplayName("JUnit test for createOneShare")
	@Test
	public void givenShare_whenCreateShare_thenReturnShare() {
		
		//given
		ShareCreateRequestDto shareRequestDto = new ShareCreateRequestDto();
		shareRequestDto.setCode("Test-Code");
		shareRequestDto.setName("Test-Name");
		shareRequestDto.setPrice(100.00);
		
		//when
		Share shareMock = Mockito.mock(Share.class);
		when(shareMock.getId()).thenReturn(1L);
		Mockito.when(shareRepository.save(ArgumentMatchers.any(Share.class))).thenReturn(shareMock);
		
		Share saveShare = shareService.createOneShare(shareRequestDto);

		//then
		assertEquals(saveShare.getId(),1L);
	}
	
	@DisplayName("JUnit test for getAllMyShares")
	@Test
	public void givenMyShareList_whenGetAllShares_thenReturnMyShareList() {
		
		//given 
		long userId = 1L;
		given(shareRepository.findByUserId(1L)).willReturn(List.of(share));
		
		//when
		List<Share>shareList = shareService.getAllMyShares(userId);
		
		//then
		assertThat(shareList).isNotNull();
		
		
	}

}
