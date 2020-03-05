package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the contractor_employee_role database table.
 * 
 */
@Entity
@Table(name="contractor_employee_role")
@NamedQuery(name="ContractorEmployeeRole.findAll", query="SELECT c FROM ContractorEmployeeRole c")
public class ContractorEmployeeRole implements Serializable {

	private static final long serialVersionUID = -1696455793429344010L;

	@Id
	@Column(name="contractor_employee_role_id")
	private long contractorEmployeeRoleId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="contractor_employee_id")
	private long contractorEmployeeId;

	@Column(name="role_id")
	private long roleId;

	private String status;

	public ContractorEmployeeRole() {
	}

	public long getContractorEmployeeRoleId() {
		return this.contractorEmployeeRoleId;
	}

	public void setContractorEmployeeRoleId(long contractorEmployeeRoleId) {
		this.contractorEmployeeRoleId = contractorEmployeeRoleId;
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

	public long getContractorEmployeeId() {
		return this.contractorEmployeeId;
	}

	public void setContractorEmployeeId(long contractorEmployeeId) {
		this.contractorEmployeeId = contractorEmployeeId;
	}

	public long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}