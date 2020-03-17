package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the employee_mapping database table.
 * 
 */
@Entity
@Table(name = "employee_mapping")
public class EmployeeMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Long employeeId;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "employee_number_maconomy")
	private String employeeNumberMaconomy;

	@Column(name = "google_account")
	private String googleAccount;

	public EmployeeMapping() {
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
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

	public String getEmployeeNumberMaconomy() {
		return this.employeeNumberMaconomy;
	}

	public void setEmployeeNumberMaconomy(String employeeNumberMaconomy) {
		this.employeeNumberMaconomy = employeeNumberMaconomy;
	}

	public String getGoogleAccount() {
		return this.googleAccount;
	}

	public void setGoogleAccount(String googleAccount) {
		this.googleAccount = googleAccount;
	}
}