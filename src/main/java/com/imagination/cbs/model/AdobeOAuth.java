package com.imagination.cbs.model;

import lombok.Data;

@Data
public class AdobeOAuth {
	
	private String accessToken;
	private String refreshToken;
	private String tokenType;
	private int expiresIn;
	
}
