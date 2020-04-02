package com.imagination.cbs.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ContractorEmployeeRequest {

	private Long roleId;
	
	private String contractorEmployeeName;
	
	private String knownAs; 
	
	private Long currencyId;
	
	private BigDecimal dayRate;
	
}
