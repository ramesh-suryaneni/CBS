package com.imagination.cbs.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ContractorEmployeeSearchDto {

	private Long contractorEmployeeId;
	private String contractorEmployeeName;
	private BigDecimal dayRate;
	private Long roleId;
	private String role;
	private Long contractorId;
	private String company;
	private Integer noOfBookingsInPast;
	private String recommandations;
	private String currencyId;
	private String currencyName;

}
