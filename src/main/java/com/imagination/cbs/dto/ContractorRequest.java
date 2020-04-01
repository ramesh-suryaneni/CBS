package com.imagination.cbs.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ContractorRequest {

	@NotNull(message = "Contractor name cannot be null")
	private String contractorName;

	@NotNull(message = "Service provided cannot be null")
	private String serviceProvided;
	
	@NotNull(message = "Contact deatils cannot be null")
	private String contactDetails;
}
