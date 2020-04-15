package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class MailRequest {
	
	private String mailTo[];
	
	private String mailFrom;
	
    private String subject;
	

    private String mailRequest;

}
