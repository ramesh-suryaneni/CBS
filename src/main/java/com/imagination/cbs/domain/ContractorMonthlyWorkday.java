package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the contractor_monthly_workdays database table.
 * 
 */
@Entity
@Table(name="contractor_monthly_workdays")
@NamedQuery(name="ContractorMonthlyWorkday.findAll", query="SELECT c FROM ContractorMonthlyWorkday c")
public class ContractorMonthlyWorkday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="work_days_id")
	private long workDaysId;

	@Column(name="booking_revision_id")
	private long bookingRevisionId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_datetime")
	private Timestamp changedDatetime;

	@Column(name="month_name")
	private String monthName;

	@Column(name="month_working_days")
	private long monthWorkingDays;

	public ContractorMonthlyWorkday() {
	}

	public long getWorkDaysId() {
		return this.workDaysId;
	}

	public void setWorkDaysId(long workDaysId) {
		this.workDaysId = workDaysId;
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

	public long getMonthWorkingDays() {
		return this.monthWorkingDays;
	}

	public void setMonthWorkingDays(long monthWorkingDays) {
		this.monthWorkingDays = monthWorkingDays;
	}

}