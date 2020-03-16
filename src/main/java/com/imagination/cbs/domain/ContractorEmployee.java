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
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="contractor_employee_id")
	private long contractorEmployeeId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="contact_details")
	private String contactDetails;

	@Column(name="employee_name")
	private String employeeName;

	@Column(name="known_as")
	private String knownAs;

	private String status;

	//bi-directional one-to-one association to ContractorEmployeeRole
	@OneToOne(mappedBy="contractorEmployee")
	private ContractorEmployeeRole contractorEmployeeRole;

	//bi-directional one-to-one association to Contractor
	@OneToOne
	@JoinColumn(name="contractor_id")
	private Contractor contractor;

	//bi-directional one-to-one association to EmployeeMapping
	@OneToOne
	@JoinColumn(name="employee_id")
	private EmployeeMapping employeeMapping;

	//bi-directional one-to-one association to ContractorEmployeeDefaultRate
	@OneToOne(mappedBy="contractorEmployee")
	private ContractorEmployeeDefaultRate contractorEmployeeDefaultRate;

	//bi-directional one-to-one association to ContractorEmployeeRating
	@OneToOne(mappedBy="contractorEmployee")
	private ContractorEmployeeRating contractorEmployeeRating;

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

	public ContractorEmployeeRole getContractorEmployeeRole() {
		return this.contractorEmployeeRole;
	}

	public void setContractorEmployeeRole(ContractorEmployeeRole contractorEmployeeRole) {
		this.contractorEmployeeRole = contractorEmployeeRole;
	}

	public Contractor getContractor() {
		return this.contractor;
	}

	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	public EmployeeMapping getEmployeeMapping() {
		return this.employeeMapping;
	}

	public void setEmployeeMapping(EmployeeMapping employeeMapping) {
		this.employeeMapping = employeeMapping;
	}

	public ContractorEmployeeDefaultRate getContractorEmployeeDefaultRate() {
		return this.contractorEmployeeDefaultRate;
	}

	public void setContractorEmployeeDefaultRate(ContractorEmployeeDefaultRate contractorEmployeeDefaultRate) {
		this.contractorEmployeeDefaultRate = contractorEmployeeDefaultRate;
	}

	public ContractorEmployeeRating getContractorEmployeeRating() {
		return this.contractorEmployeeRating;
	}

	public void setContractorEmployeeRating(ContractorEmployeeRating contractorEmployeeRating) {
		this.contractorEmployeeRating = contractorEmployeeRating;
	}

}