package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class InternalResourceEmail {

	private String contractorName;

	private String descipline;

	private String role;
	
	private String jobNumber;
	
	private String supplierType;

	private String contractedFromDate;

	private String contractedToDate;
	
	private MailRequest mailRequest;

}
