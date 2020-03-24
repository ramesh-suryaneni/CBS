package com.imagination.cbs.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class BookingDto {
	private String bookingId;
	private String bookingDescription;
	private String changedBy;
	private String changedDate;

	@NotNull(message = "Role Id cannot be null")
	private String roleId;

	@NotNull(message = "Discipline Id cannot be null")
	private String disciplineId;

	@NotNull(message = "Start Date cannot be null")
	private String startDate;

	@NotNull(message = "End Date cannot be null")
	private String endDate;

	@NotNull(message = "Job Number cannot be null")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Job Number should be alpha numberic")
	private String jobNumber;

	private String teamId;

	private String bookingRevisionId;
	private String agreementDocumentId;
	private String agreementId;
	private String approvalStatusId;

	@Pattern(regexp = "^\\d+\\.\\d*$", message = "Contract Amount After Tax should be decimal")
	private String contractAmountAftertax;

	@NotNull(message = "Contract Amount Before Tax cannot be null")
	@Pattern(regexp = "^\\d+\\.\\d*$", message = "Contract Amount Before Tax should be decimal")
	private String contractAmountBeforetax;

	private String contractedFromDate;

	private String contractedToDate;

	private String contractorContactDetails;

	private String contractorEmployeeName;

	private String contractorEmployeeRoleId;

	@Pattern(regexp = "[0-9]+", message = "Contractor Id should be numeric only")
	private String contractorId;

	private String contractorName;

	private String contractorSignedDate;

	@NotNull(message = "Contractor Total Available Days cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Contractor Total Available Days should be numbers only")
	private String contractorTotalAvailableDays;

	@Pattern(regexp = "[0-9]+", message = "Contractor Total Working Days should be numbers only")
	private String contractorTotalWorkingDays;

	private String contractorType;

	private String currencyId;

	private String employeeContactDetails;

	@Pattern(regexp = "[0-9]+", message = "Employer Tax Percent should be numeric only")
	private String employerTaxPercent;

	private String insideIr35;

	private String knownAs;

	private String officeDescription;

	private String officeId;

	@NotNull(message = "Rate cannot be null")
	private String rate;

	private String jobDeptName;

	@NotNull(message = "Supplier Type Id cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Supplier Type Id should be numeric only")
	private String supplierTypeId;

	private String approverComments;

	@NotNull(message = "Commisioning Office cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Commisionning Office Id should be numeric only")
	private String commisioningOffice;

	private String contractWorkLocation;

	@NotNull(message = "Reason for Recruiting cannot be null")
	@Pattern(regexp = "[0-9]+", message = "Reason for Recruiting should be numeric only")
	private String reasonForRecruiting;

	private String contractEmployeeId;

	private List<WorkDaysDto> workDays;

	private List<WorkTasksDto> workTasks;

	private String jobname;

	private String supplierWorkLocationType;

}