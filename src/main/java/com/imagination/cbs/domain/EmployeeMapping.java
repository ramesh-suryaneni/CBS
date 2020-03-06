package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;


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
	@Column(name="employee_id")
	private long employeeId;

	@Column(name="employee_number_maconomy")
	private long employeeNumberMaconomy;

	@Column(name="ogle_account")
	private String ogleAccount;

	//bi-directional one-to-one association to Approver
	@OneToOne
	@JoinColumn(name="employee_id", referencedColumnName="employe_id")
	private Approver approver;

	public EmployeeMapping() {
	}

	public long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
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

}