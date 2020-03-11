package com.imagination.cbs.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ContractorDto {

	@Getter
	@Setter
	private long contractorId;
	
	@Getter
	@Setter
	private String contractorName;

	@Override
	public String toString() {
		return "ContractorDto [contractorId=" + contractorId + ", contractorName=" + contractorName + "]";
	}
	
	
}
