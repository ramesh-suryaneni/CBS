package com.imagination.cbs.dto;

import java.math.BigInteger;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class BookingDashBoardDto {
	
	private String status;
	private String jobname;
	private String roleName;
	private String contractorName;
	private Timestamp contractedFromDate;
	private Timestamp contractedToDate;
	private String changedBy;
	private Timestamp changedDate;
	private BigInteger bookingId;
	
}

