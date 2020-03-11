package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class ContractorDto {

	private long contractorId;
	
	private String contractorName;

	@Override
	public String toString() {
		return "ContractorDto [contractorId=" + contractorId + ", contractorName=" + contractorName + "]";
	}
	
	
}
