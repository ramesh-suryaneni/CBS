package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class InternalResourceEmailDto {
	
	
	private String contractorName;

	private Long disciplineId;

	private Long roleId;
	
	private String jobNumber;
	
	private String supplierType;

	private String contractedFromDate;

	private String contractedToDate;

}
