package com.imagination.cbs.model;

import lombok.Data;

@Data
public class AdobeOAuth {
	private String access_token;
	private String refresh_token;
	private String token_type;
	private String expires_in;
}
