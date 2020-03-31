package com.imagination.cbs.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookingDto {
	private String bookingId;
	private String bookingDescription;
	private String changedBy;
	private String changedDate;

	private String jobNumber;

	private String bookingRevisionId;
	private String agreementDocumentId;
	private String agreementId;

	private String contractAmountAftertax;

	private String contractAmountBeforetax;

	private String contractedFromDate;

	private String contractedToDate;

	private String contractorContactDetails;

	private String contractorEmployeeName;

	private String contractorEmployeeRoleId;

	private String contractorId;

	private String contractorName;

	private String contractorSignedDate;

	private String contractorTotalAvailableDays;

	private String contractorTotalWorkingDays;

	private String contractorType;

	private String currencyId;

	private String employeeContactDetails;

	private String employerTaxPercent;

	private String insideIr35;

	private String rate;

	private String jobDeptName;

	private String supplierTypeId;

	private String approverComments;

	private String commisioningOffice;

	private String contractWorkLocation;

	private String reasonForRecruiting;

	private String contractEmployeeId;

	private List<WorkDaysDto> workDays;

	private List<WorkTasksDto> workTasks;

	private String jobname;

	private String supplierWorkLocationType;

	private TeamDto team;

	private ApprovalStatusDmDto approvalStatusDm;

	private ContractorRoleDto contractorRole;

	private DisciplineDto discipline;

	private ContractorDto contractor;

	private SupplierTypeDto supplierType;

	private SupplierWorkLocationTypeDto supplierWorkLocation;

	private RecruitingDto recruitingReason;

	private OfficeDto office;

	private CurrencyDto currency;

	private ContractorEmployeeDto contractorEmployee;
}