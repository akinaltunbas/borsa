package com.project.borsa.dto;

import java.util.Date;

import com.project.borsa.entities.Share;

public class ShareResponseDto {
	
	private Long id;
	private String code;
	private String name;
	private Date date;
	
	public ShareResponseDto(Share entity) {
		this.id = entity.getId();
		this.code = entity.getCode();
		this.name = entity.getName();
		this.date = entity.getDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	

}
