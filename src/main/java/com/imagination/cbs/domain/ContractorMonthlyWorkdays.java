package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.imagination.cbs.domain.BookingRevision;
/**
 * The persistent class for the contractor_monthly_workdays database table.
 * 
 */
@Entity
@Table(name="contractor_monthly_workdays")
@NamedQuery(name="ContractorMonthlyWorkdays.findAll", query="SELECT c from ContractorMonthlyWorkdays c")
public class ContractorMonthlyWorkdays implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="work_days_id")
	private long workDaysId;
	
	@Column(name="month_name")
	private String monthName;
	
	@Column(name="month_working_days")
	private long monthWorkingDays;
	
	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_datetime")
	private Timestamp changedDateTime;

	@OneToOne
	@JoinColumn(name="booking_revision_id")
	private BookingRevision bookingRevision;

	public long getWorkDaysId() {
		return workDaysId;
	}

	public void setWorkDaysId(long workDaysId) {
		this.workDaysId = workDaysId;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public long getMonthWorkingDays() {
		return monthWorkingDays;
	}

	public void setMonthWorkingDays(long monthWorkingDays) {
		this.monthWorkingDays = monthWorkingDays;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Timestamp getChangedDateTime() {
		return changedDateTime;
	}

	public void setChangedDateTime(Timestamp changedDateTime) {
		this.changedDateTime = changedDateTime;
	}

	public BookingRevision getBookingRevision() {
		return bookingRevision;
	}

	public void setBookingRevision(BookingRevision bookingRevision) {
		this.bookingRevision = bookingRevision;
	}
	
}
