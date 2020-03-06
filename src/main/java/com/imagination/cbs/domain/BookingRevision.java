package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the booking_revision database table.
 * 
 */
@Entity
@Table(name="booking_revision")
@NamedQuery(name="BookingRevision.findAll", query="SELECT b FROM BookingRevision b")
public class BookingRevision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="booking_revision_id")
	private long bookingRevisionId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="contracted_from_date")
	private Timestamp contractedFromDate;

	@Column(name="contracted_to_date")
	private Timestamp contractedToDate;

	@Column(name="job_number")
	private long jobNumber;

	private BigDecimal rate;

	@Column(name="revision_number")
	private long revisionNumber;

	//bi-directional many-to-one association to Booking
	@ManyToOne
	@JoinColumn(name="booking_id")
	private Booking booking;

	//bi-directional one-to-one association to ContractorEmployeeRole
	@OneToOne
	@JoinColumn(name="contractor_employee_role_id")
	private ContractorEmployeeRole contractorEmployeeRole;

	//bi-directional one-to-one association to CurrencyDm
	@OneToOne(mappedBy="bookingRevision")
	private CurrencyDm currencyDm;

	//bi-directional one-to-one association to ApprovalStatusDm
	@OneToOne
	@JoinColumn(name="approval_status_id")
	private ApprovalStatusDm approvalStatusDm;

	//bi-directional one-to-one association to ContractorEmployeeRating
	@OneToOne(mappedBy="bookingRevision")
	private ContractorEmployeeRating contractorEmployeeRating;

	public BookingRevision() {
	}

	public long getBookingRevisionId() {
		return this.bookingRevisionId;
	}

	public void setBookingRevisionId(long bookingRevisionId) {
		this.bookingRevisionId = bookingRevisionId;
	}

	public String getChangedBy() {
		return this.changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Timestamp getChangedDate() {
		return this.changedDate;
	}

	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
	}

	public Timestamp getContractedFromDate() {
		return this.contractedFromDate;
	}

	public void setContractedFromDate(Timestamp contractedFromDate) {
		this.contractedFromDate = contractedFromDate;
	}

	public Timestamp getContractedToDate() {
		return this.contractedToDate;
	}

	public void setContractedToDate(Timestamp contractedToDate) {
		this.contractedToDate = contractedToDate;
	}

	public long getJobNumber() {
		return this.jobNumber;
	}

	public void setJobNumber(long jobNumber) {
		this.jobNumber = jobNumber;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public long getRevisionNumber() {
		return this.revisionNumber;
	}

	public void setRevisionNumber(long revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public Booking getBooking() {
		return this.booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public ContractorEmployeeRole getContractorEmployeeRole() {
		return this.contractorEmployeeRole;
	}

	public void setContractorEmployeeRole(ContractorEmployeeRole contractorEmployeeRole) {
		this.contractorEmployeeRole = contractorEmployeeRole;
	}

	public CurrencyDm getCurrencyDm() {
		return this.currencyDm;
	}

	public void setCurrencyDm(CurrencyDm currencyDm) {
		this.currencyDm = currencyDm;
	}

	public ApprovalStatusDm getApprovalStatusDm() {
		return this.approvalStatusDm;
	}

	public void setApprovalStatusDm(ApprovalStatusDm approvalStatusDm) {
		this.approvalStatusDm = approvalStatusDm;
	}

	public ContractorEmployeeRating getContractorEmployeeRating() {
		return this.contractorEmployeeRating;
	}

	public void setContractorEmployeeRating(ContractorEmployeeRating contractorEmployeeRating) {
		this.contractorEmployeeRating = contractorEmployeeRating;
	}

}