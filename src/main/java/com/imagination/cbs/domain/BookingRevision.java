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

	private static final long serialVersionUID = -5990845334574111501L;

	@Id
	@Column(name="booking_revision_id")
	private long bookingRevisionId;

	@Column(name="approval_status_id")
	private long approvalStatusId;

	@Column(name="booking_id")
	private long bookingId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="contracted_from_date")
	private Timestamp contractedFromDate;

	@Column(name="contracted_to_date")
	private Timestamp contractedToDate;

	@Column(name="contractor_employee_role_id")
	private long contractorEmployeeRoleId;

	@Column(name="currency_id")
	private long currencyId;

	@Column(name="job_number")
	private long jobNumber;

	private BigDecimal rate;

	@Column(name="revision_number")
	private long revisionNumber;

	public BookingRevision() {
	}

	public long getBookingRevisionId() {
		return this.bookingRevisionId;
	}

	public void setBookingRevisionId(long bookingRevisionId) {
		this.bookingRevisionId = bookingRevisionId;
	}

	public long getApprovalStatusId() {
		return this.approvalStatusId;
	}

	public void setApprovalStatusId(long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public long getBookingId() {
		return this.bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
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

	public long getContractorEmployeeRoleId() {
		return this.contractorEmployeeRoleId;
	}

	public void setContractorEmployeeRoleId(long contractorEmployeeRoleId) {
		this.contractorEmployeeRoleId = contractorEmployeeRoleId;
	}

	public long getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
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

}