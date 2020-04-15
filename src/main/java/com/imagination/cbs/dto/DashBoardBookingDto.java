package com.imagination.cbs.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class DashBoardBookingDto {
	
	private String status;
	private String jobName;
	private String roleName;
	private String contractorName;
	private String contractedFromDate;
	private String contractedToDate;
	private String changedBy;
	private String changedDate;
	private BigInteger bookingId;
	private BigInteger bookingRevisionId;

}

