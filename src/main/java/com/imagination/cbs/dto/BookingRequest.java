package com.imagination.cbs.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class BookingRequest {
	@NotNull(message = "Role Id cannot be null")
	private String roleId;

	@NotNull(message = "Job Number cannot be null")
	private String jobNumber;

	@Pattern(regexp = "[0-9]+", message = "Contractor Id should be numeric only")
	private String contractorId;

	@NotNull(message = "Supplier Type Id cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Supplier Type Id should be numeric only")
	private String supplierTypeId;

	@NotNull(message = "Commisioning Office cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Commisionning Office Id should be numeric only")
	private String commisioningOffice;

	@NotNull(message = "Reason for Recruiting cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Reason for Recruiting should be numeric only")
	private String reasonForRecruiting;

	private String contractWorkLocation;

	@NotNull(message = "Contractor Total Available Days cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Contractor Total Available Days should be numbers only")
	private String contractorTotalAvailableDays;

	@Pattern(regexp = "[0-9]+", message = "Contractor Total Working Days should be numbers only")
	private String contractorTotalWorkingDays;

	@Pattern(regexp = "^\\d+\\.\\d*$", message = "Contract Amount After Tax should be decimal")
	private String contractAmountAftertax;

	@NotNull(message = "Contract Amount Before Tax cannot be null")
	@Pattern(regexp = "^\\d+\\.\\d*$", message = "Contract Amount Before Tax should be decimal")
	private String contractAmountBeforetax;

	@Pattern(regexp = "[0-9]+", message = "Employer Tax Percent should be numeric only")
	private String employerTaxPercent;

	@NotNull(message = "Rate cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Contractor Rate Per Day should be numeric only")
	private String rate;

	private String supplierWorkLocationType;

	@NotNull(message = "Currency Id cannot be null")
	private String currencyId;

	private List<WorkTasksDto> workTasks;

	private String bookingDescription;

	@NotNull(message = "Start Date cannot be null")
	private String contractedFromDate;

	@NotNull(message = "End Date cannot be null")
	private String contractedToDate;

	private String contractorSignedDate;

	private List<WorkDaysDto> workDays;

	private String contractEmployeeId;

	private String insideIr35;

	private String jobDeptName;

	private String commOffRegion;

	private String contractorWorkRegion;
}
