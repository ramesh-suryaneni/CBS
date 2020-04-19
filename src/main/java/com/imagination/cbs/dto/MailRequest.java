package com.imagination.cbs.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MailRequest {
	
	private String mailTo[];
	
	private String mailFrom;
	
    private String subject;
	

    private String mailRequest;
    
    

}
