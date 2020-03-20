package com.imagination.cbs.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ContractorIndexDto {

	private String supplierName;
	
	private String contractorName;
	
	private String alias;
	
	private String discipline;
	
	private String role;
	
	private BigDecimal rate;
	
	private String latestProject;
	
	private BigDecimal rating;
	
	private String comments;
}
