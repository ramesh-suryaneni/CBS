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

	private String contractorSignedDate;

	private String contractorTotalAvailableDays;

	private String contractorTotalWorkingDays;

	private String employerTaxPercent;

	private String rate;

	private String jobDeptName;

	private String approverComments;

	private List<WorkDaysDto> monthlyWorkDays;

	private List<WorkTasksDto> bookingWorkTasks;

	private String jobname;

	private TeamDto team;

	private ApprovalStatusDmDto approvalStatus;

	private RoleDto role;

	private DisciplineDto discipline;

	private ContractorDto contractor;

	private SupplierTypeDto supplierType;

	private RecruitingDto reasonForRecruiting;

	private OfficeDto commisioningOffice;

	private OfficeDto contractWorkLocation;

	private CurrencyDto currency;

	private ContractorEmployeeDto contractEmployee;

	private RegionDto commOffRegion;

	private RegionDto contractorWorkRegion;

	private List<ContractorWorkSiteDto> contractorWorkSites;
	
	private String insideIr35;
}