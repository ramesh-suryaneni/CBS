package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the employee_mapping database table.
 * 
 */
@Entity
@Table(name="employee_mapping")
@NamedQuery(name="EmployeeMapping.findAll", query="SELECT e FROM EmployeeMapping e")
public class EmployeeMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employee_id")
	private long employeeId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="employee_number_maconomy")
	private long employeeNumberMaconomy;

	@Column(name="ogle_account")
	private String ogleAccount;

	//bi-directional one-to-one association to Approver
	@OneToOne(mappedBy="employeeMapping")
	private Approver approver;

	//bi-directional one-to-one association to ContractorEmployee
	@OneToOne(mappedBy="employeeMapping")
	private ContractorEmployee contractorEmployee;

	public EmployeeMapping() {
	}

	public long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(long employeeId) {
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

	public long getEmployeeNumberMaconomy() {
		return this.employeeNumberMaconomy;
	}

	public void setEmployeeNumberMaconomy(long employeeNumberMaconomy) {
		this.employeeNumberMaconomy = employeeNumberMaconomy;
	}

	public String getOgleAccount() {
		return this.ogleAccount;
	}

	public void setOgleAccount(String ogleAccount) {
		this.ogleAccount = ogleAccount;
	}

	public Approver getApprover() {
		return this.approver;
	}

	public void setApprover(Approver approver) {
		this.approver = approver;
	}

	public ContractorEmployee getContractorEmployee() {
		return this.contractorEmployee;
	}

	public void setContractorEmployee(ContractorEmployee contractorEmployee) {
		this.contractorEmployee = contractorEmployee;
	}

}