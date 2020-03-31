package com.imagination.cbs.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ContractorEmployeeDto {

	private Long contractorEmployeeId;
	private String contractorEmployeeName;
	private BigDecimal dayRate;
	private Long roleId;
	private String role;
	private Long contractorId;
	private String company;
	private int noOfBookingsInPast;
	private String recommandations;

}