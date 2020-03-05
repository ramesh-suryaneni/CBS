package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the contractor_employee database table.
 * 
 */
@Entity
@Table(name="contractor_employee")
@NamedQuery(name="ContractorEmployee.findAll", query="SELECT c FROM ContractorEmployee c")
public class ContractorEmployee implements Serializable {

	private static final long serialVersionUID = 6973491865093487804L;

	@Id
	@Column(name="contractor_employee_id")
	private long contractorEmployeeId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="contact_details")
	private String contactDetails;

	@Column(name="contractor_id")
	private long contractorId;

	@Column(name="employee_id")
	private long employeeId;

	@Column(name="employee_name")
	private String employeeName;

	@Column(name="known_as")
	private String knownAs;

	private String status;

	public ContractorEmployee() {
	}

	public long getContractorEmployeeId() {
		return this.contractorEmployeeId;
	}

	public void setContractorEmployeeId(long contractorEmployeeId) {
		this.contractorEmployeeId = contractorEmployeeId;
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

	public String getContactDetails() {
		return this.contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public long getContractorId() {
		return this.contractorId;
	}

	public void setContractorId(long contractorId) {
		this.contractorId = contractorId;
	}

	public long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getKnownAs() {
		return this.knownAs;
	}

	public void setKnownAs(String knownAs) {
		this.knownAs = knownAs;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}