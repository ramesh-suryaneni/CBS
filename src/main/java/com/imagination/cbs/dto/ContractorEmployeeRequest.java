package com.imagination.cbs.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ContractorEmployeeRequest {

	@NotEmpty(message = "contractorEmployeeName must be non-empty")
	private String contractorEmployeeName;
	
	@NotEmpty(message = "currencyId must be non-empty")
	@Pattern(regexp = "^\\d+$", message = "currencyId should be numeric only")
	private String currencyId;
	
	@NotEmpty(message = "dayRate must be non-empty")
	@Pattern(regexp = "^\\d+\\.?\\d+$", message = "dayRate should be decimal")
	private String dayRate;
	
	private String knownAs; 
	
	private String roleId;
	
}
