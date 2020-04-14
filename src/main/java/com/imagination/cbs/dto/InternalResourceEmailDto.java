package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class InternalResourceEmailDto {
	
	
	private Long disciplineId;

	private Long roleId;
	
	private String jobNumber;
	
	private String contractedFromDate;

	private String contractedToDate;

}
