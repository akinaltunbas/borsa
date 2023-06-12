package com.project.borsa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.borsa.dto.ShareCreateRequestDto;
import com.project.borsa.dto.ShareUpdateRequestDto;
import com.project.borsa.entities.Share;
import com.project.borsa.repositories.ShareRepository;

@Service
public class ShareServiceImpl implements ShareService {
	
	private ShareRepository shareRepository;
	
	

	public ShareServiceImpl(ShareRepository shareRepository) {
		this.shareRepository = shareRepository;
	}
	
	@Override
	public List<Share> getAllShares() {
		return shareRepository.findAll();
	}
	
	@Override
	public Share createOneShare(ShareCreateRequestDto newShareRequest) {
		Share share = new Share();
		newShareRequest.mapShareCreateDtoToShare(share);
		return shareRepository.save(share);
	}
	
	@Override
	public Share getShareById(Long shareId) {
		return shareRepository.findById(shareId).orElse(null);
	}
	
	@Override
	public Share updateOneShareById(Long shareId, ShareUpdateRequestDto updateShareRequest) {
		Optional<Share> share = shareRepository.findById(shareId);
		if(share.isPresent()) {
			Share share1 = share.get();
			updateShareRequest.mapShareUpdateRequestDtoToShare(share1);
			shareRepository.save(share1);
			return share1;
			
		}else
			
		return null;
	}
	
	@Override
	public void deleteOneShareById(Long shareId) {
		shareRepository.deleteById(shareId);
		
	}
	
	@Override
	public List<Share> getAllMyShares(Long userId) {
		return shareRepository.findByUserId(userId);
	
	}






}
