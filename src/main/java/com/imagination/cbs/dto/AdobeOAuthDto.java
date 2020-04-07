package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class AdobeOAuthDto {
	
	private String accessToken;
	private String refreshToken;
	private String tokenType;
	private int expiresIn;
	
}
