package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the contractor_employee_role database table.
 * 
 */
@Entity
@Table(name = "contractor_employee_role")
@NamedQuery(name = "ContractorEmployeeRole.findAll", query = "SELECT c FROM ContractorEmployeeRole c")
public class ContractorEmployeeRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "contractor_employee_role_id")
	private Long contractorEmployeeRoleId;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	private String status;

	// bi-directional one-to-one association to BookingRevision
	@OneToOne(mappedBy = "contractorEmployeeRole")
	private BookingRevision bookingRevision;

	// bi-directional one-to-one association to RoleDm
	@OneToOne
	@JoinColumn(name = "role_id")
	private RoleDm roleDm;

	// bi-directional one-to-one association to ContractorEmployee
	@OneToOne
	@JoinColumn(name = "contractor_employee_id")
	private ContractorEmployee contractorEmployee;

	public ContractorEmployeeRole() {
	}

	public Long getContractorEmployeeRoleId() {
		return this.contractorEmployeeRoleId;
	}

	public void setContractorEmployeeRoleId(Long contractorEmployeeRoleId) {
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BookingRevision getBookingRevision() {
		return this.bookingRevision;
	}

	public void setBookingRevision(BookingRevision bookingRevision) {
		this.bookingRevision = bookingRevision;
	}

	public RoleDm getRoleDm() {
		return this.roleDm;
	}

	public void setRoleDm(RoleDm roleDm) {
		this.roleDm = roleDm;
	}

	public ContractorEmployee getContractorEmployee() {
		return this.contractorEmployee;
	}

	public void setContractorEmployee(ContractorEmployee contractorEmployee) {
		this.contractorEmployee = contractorEmployee;
	}

}