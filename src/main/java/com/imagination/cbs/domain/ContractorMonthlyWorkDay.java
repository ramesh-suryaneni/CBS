package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

/**
 * The persistent class for the contractor_monthly_workdays database table.
 * 
 */
@Entity
@Table(name = "contractor_monthly_workdays")
public class ContractorMonthlyWorkDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "work_days_id")
	private Long workDaysId;

	@Column(name = "booking_revision_id")
	private Long bookingRevisionId;

	@Column(name = "changed_by")
	private String changedBy;

	@CreationTimestamp
	@Column(name = "changed_datetime")
	private Timestamp changedDatetime;

	@Column(name = "month_name")
	private String monthName;

	@Column(name = "month_working_days")
	private Long monthWorkingDays;

	public ContractorMonthlyWorkDay() {
	}

	public Long getWorkDaysId() {
		return this.workDaysId;
	}

	public void setWorkDaysId(Long workDaysId) {
		this.workDaysId = workDaysId;
	}

	public Long getBookingRevisionId() {
		return this.bookingRevisionId;
	}

	public void setBookingRevisionId(Long bookingRevisionId) {
		this.bookingRevisionId = bookingRevisionId;
	}

	public String getChangedBy() {
		return this.changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Timestamp getChangedDatetime() {
		return this.changedDatetime;
	}

	public void setChangedDatetime(Timestamp changedDatetime) {
		this.changedDatetime = changedDatetime;
	}

	public String getMonthName() {
		return this.monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public Long getMonthWorkingDays() {
		return this.monthWorkingDays;
	}

	public void setMonthWorkingDays(Long monthWorkingDays) {
		this.monthWorkingDays = monthWorkingDays;
	}

}