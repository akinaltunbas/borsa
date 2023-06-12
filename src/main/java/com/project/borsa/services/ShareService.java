package com.project.borsa.services;

import java.util.List;

import com.project.borsa.dto.ShareCreateRequestDto;
import com.project.borsa.dto.ShareUpdateRequestDto;
import com.project.borsa.entities.Share;

public interface ShareService {
	
	public List<Share> getAllShares();
	
	public Share createOneShare(ShareCreateRequestDto newShareRequest);
	
	public Share updateOneShareById(Long shareId, ShareUpdateRequestDto updateShareRequest);
	
	public void deleteOneShareById(Long shareId);
	
	public List<Share> getAllMyShares(Long userId);
	
	public Share getShareById(Long shareId);

}
