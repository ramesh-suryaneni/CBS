package com.imagination.cbs.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

/**
 * The persistent class for the booking_revision database table.
 * 
 */
@Entity
@Table(name = "booking_revision")
public class BookingRevision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_revision_id")
	private Long bookingRevisionId;

	@Column(name = "agreement_document_id")
	private String agreementDocumentId;

	@Column(name = "agreement_id")
	private String agreementId;

	@Column(name = "approval_status_id")
	private Long approvalStatusId;

	@Column(name = "changed_by")
	private String changedBy;

	@CreationTimestamp
	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "contract_amount_aftertax")
	private BigDecimal contractAmountAftertax;

	@Column(name = "contract_amount_beforetax")
	private BigDecimal contractAmountBeforetax;

	@Column(name = "contracted_from_date")
	private Timestamp contractedFromDate;

	@Column(name = "contracted_to_date")
	private Timestamp contractedToDate;

	@Column(name = "contractor_contact_details")
	private String contractorContactDetails;

	@Column(name = "contractor_employee_name")
	private String contractorEmployeeName;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private RoleDm role;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contractor_id")
	private Contractor contractor;

	@Column(name = "contractor_name")
	private String contractorName;

	@Column(name = "contractor_signed_date")
	private Timestamp contractorSignedDate;

	@Column(name = "contractor_total_available_days")
	private Long contractorTotalAvailableDays;

	@Column(name = "contractor_total_working_days")
	private Long contractorTotalWorkingDays;

	@Column(name = "contractor_type")
	private String contractorType;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	private CurrencyDm currency;

	@Column(name = "employee_contact_details")
	private String employeeContactDetails;

	@Column(name = "employer_tax_percent")
	private BigDecimal employerTaxPercent;

	@Column(name = "inside_ir35")
	private String insideIr35;

	@Column(name = "job_number")
	private String jobNumber;

	@Column(name = "known_as")
	private String knownAs;

	private BigDecimal rate;

	@Column(name = "revision_number")
	private Long revisionNumber;

	@Column(name = "job_dept_name")
	private String jobDeptName;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_type_id")
	private SupplierTypeDm supplierType;

	@Column(name = "appprover_comments")
	private String approverComments;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commissioning_office")
	private OfficeDm commisioningOffice;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contract_work_location")
	private OfficeDm contractWorkLocation;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reason_for_recruiting")
	private ReasonsForRecruiting reasonForRecruiting;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contract_employee_id")
	private ContractorEmployee contractEmployee;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "team_id")
	private Team team;

	@Column(name = "job_name")
	private String jobname;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_work_location_type")
	private SupplierWorkLocationTypeDm supplierWorkLocationType;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "booking_id")
	private Booking booking;

	@OneToMany(mappedBy = "bookingRevision", cascade = CascadeType.ALL)
	private List<ContractorMonthlyWorkDay> monthlyWorkDays;

	@OneToMany(mappedBy = "bookingRevision", cascade = CascadeType.ALL)
	private List<BookingWorkTask> bookingWorkTasks;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "comm_off_region")
	private Region commOffRegion;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contractor_work_region")
	private Region contractorWorkRegion;

	public Region getCommOffRegion() {
		return commOffRegion;
	}

	public void setCommOffRegion(Region commOffRegion) {
		this.commOffRegion = commOffRegion;
	}

	public Region getContractorWorkRegion() {
		return contractorWorkRegion;
	}

	public void setContractorWorkRegion(Region contractorWorkRegion) {
		this.contractorWorkRegion = contractorWorkRegion;
	}

	public List<ContractorMonthlyWorkDay> getMonthlyWorkDays() {
		return monthlyWorkDays;
	}

	public void setMonthlyWorkDays(List<ContractorMonthlyWorkDay> monthlyWorkDays) {
		this.monthlyWorkDays = monthlyWorkDays;
	}

	public List<BookingWorkTask> getBookingWorkTasks() {
		return bookingWorkTasks;
	}

	public void setBookingWorkTasks(List<BookingWorkTask> bookingWorkTasks) {
		this.bookingWorkTasks = bookingWorkTasks;
	}

	public CurrencyDm getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyDm currencyDm) {
		this.currency = currencyDm;
	}

	public RoleDm getRole() {
		return role;
	}

	public void setRole(RoleDm role) {
		this.role = role;
	}

	public Long getBookingRevisionId() {
		return bookingRevisionId;
	}

	public void setBookingRevisionId(Long bookingRevisionId) {
		this.bookingRevisionId = bookingRevisionId;
	}

	public String getAgreementDocumentId() {
		return agreementDocumentId;
	}

	public void setAgreementDocumentId(String agreementDocumentId) {
		this.agreementDocumentId = agreementDocumentId;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public Long getApprovalStatusId() {
		return approvalStatusId;
	}

	public void setApprovalStatusId(Long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Timestamp getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
	}

	public BigDecimal getContractAmountAftertax() {
		return contractAmountAftertax;
	}

	public void setContractAmountAftertax(BigDecimal contractAmountAftertax) {
		this.contractAmountAftertax = contractAmountAftertax;
	}

	public BigDecimal getContractAmountBeforetax() {
		return contractAmountBeforetax;
	}

	public void setContractAmountBeforetax(BigDecimal contractAmountBeforetax) {
		this.contractAmountBeforetax = contractAmountBeforetax;
	}

	public Timestamp getContractedFromDate() {
		return contractedFromDate;
	}

	public void setContractedFromDate(Timestamp contractedFromDate) {
		this.contractedFromDate = contractedFromDate;
	}

	public Timestamp getContractedToDate() {
		return contractedToDate;
	}

	public void setContractedToDate(Timestamp contractedToDate) {
		this.contractedToDate = contractedToDate;
	}

	public String getContractorContactDetails() {
		return contractorContactDetails;
	}

	public void setContractorContactDetails(String contractorContactDetails) {
		this.contractorContactDetails = contractorContactDetails;
	}

	public String getContractorEmployeeName() {
		return contractorEmployeeName;
	}

	public void setContractorEmployeeName(String contractorEmployeeName) {
		this.contractorEmployeeName = contractorEmployeeName;
	}

	public Contractor getContractor() {
		return contractor;
	}

	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public Timestamp getContractorSignedDate() {
		return contractorSignedDate;
	}

	public void setContractorSignedDate(Timestamp contractorSignedDate) {
		this.contractorSignedDate = contractorSignedDate;
	}

	public Long getContractorTotalAvailableDays() {
		return contractorTotalAvailableDays;
	}

	public void setContractorTotalAvailableDays(Long contractorTotalAvailableDays) {
		this.contractorTotalAvailableDays = contractorTotalAvailableDays;
	}

	public Long getContractorTotalWorkingDays() {
		return contractorTotalWorkingDays;
	}

	public void setContractorTotalWorkingDays(Long contractorTotalWorkingDays) {
		this.contractorTotalWorkingDays = contractorTotalWorkingDays;
	}

	public String getContractorType() {
		return contractorType;
	}

	public void setContractorType(String contractorType) {
		this.contractorType = contractorType;
	}

	public String getEmployeeContactDetails() {
		return employeeContactDetails;
	}

	public void setEmployeeContactDetails(String employeeContactDetails) {
		this.employeeContactDetails = employeeContactDetails;
	}

	public BigDecimal getEmployerTaxPercent() {
		return employerTaxPercent;
	}

	public void setEmployerTaxPercent(BigDecimal employerTaxPercent) {
		this.employerTaxPercent = employerTaxPercent;
	}

	public String getInsideIr35() {
		return insideIr35;
	}

	public void setInsideIr35(String insideIr35) {
		this.insideIr35 = insideIr35;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getKnownAs() {
		return knownAs;
	}

	public void setKnownAs(String knownAs) {
		this.knownAs = knownAs;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Long getRevisionNumber() {
		return revisionNumber;
	}

	public void setRevisionNumber(Long revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public String getJobDeptName() {
		return jobDeptName;
	}

	public void setJobDeptName(String jobDeptName) {
		this.jobDeptName = jobDeptName;
	}

	public SupplierTypeDm getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(SupplierTypeDm supplierType) {
		this.supplierType = supplierType;
	}

	public String getApproverComments() {
		return approverComments;
	}

	public void setApproverComments(String approverComments) {
		this.approverComments = approverComments;
	}

	public OfficeDm getCommisioningOffice() {
		return commisioningOffice;
	}

	public void setCommisioningOffice(OfficeDm commisioningOffice) {
		this.commisioningOffice = commisioningOffice;
	}

	public OfficeDm getContractWorkLocation() {
		return contractWorkLocation;
	}

	public void setContractWorkLocation(OfficeDm contractWorkLocation) {
		this.contractWorkLocation = contractWorkLocation;
	}

	public ReasonsForRecruiting getReasonForRecruiting() {
		return reasonForRecruiting;
	}

	public void setReasonForRecruiting(ReasonsForRecruiting reasonForRecruiting) {
		this.reasonForRecruiting = reasonForRecruiting;
	}

	public ContractorEmployee getContractEmployee() {
		return contractEmployee;
	}

	public void setContractEmployee(ContractorEmployee contractEmployee) {
		this.contractEmployee = contractEmployee;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public SupplierWorkLocationTypeDm getSupplierWorkLocationType() {
		return supplierWorkLocationType;
	}

	public void setSupplierWorkLocationType(SupplierWorkLocationTypeDm supplierWorkLocationType) {
		this.supplierWorkLocationType = supplierWorkLocationType;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

}