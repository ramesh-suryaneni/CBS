package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the role_dm database table.
 * 
 */
@Entity
@Table(name = "role_dm")
@NamedQuery(name = "RoleDm.findAll", query = "SELECT r FROM RoleDm r")
public class RoleDm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "discipline_id")
	private Long disciplineId;

	@Column(name = "role_description")
	private String roleDescription;

	@Column(name = "role_name")
	private String roleName;
	
	@Column(name="inside_ir35")
	private String insideIr35;
	
	@Column(name="status")
	private String status;

	// bi-directional one-to-one association to ContractorEmployeeRole
	@OneToOne(mappedBy = "roleDm")
	private ContractorEmployeeRole contractorEmployeeRole;

	// bi-directional one-to-one association to RoleDefaultRate
	@OneToOne
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	private RoleDefaultRate roleDefaultRate;

	public RoleDm() {
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	public Long getDisciplineId() {
		return this.disciplineId;
	}

	public void setDisciplineId(Long disciplineId) {
		this.disciplineId = disciplineId;
	}

	public String getRoleDescription() {
		return this.roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public ContractorEmployeeRole getContractorEmployeeRole() {
		return this.contractorEmployeeRole;
	}

	public void setContractorEmployeeRole(ContractorEmployeeRole contractorEmployeeRole) {
		this.contractorEmployeeRole = contractorEmployeeRole;
	}

	public RoleDefaultRate getRoleDefaultRate() {
		return this.roleDefaultRate;
	}

	public void setRoleDefaultRate(RoleDefaultRate roleDefaultRate) {
		this.roleDefaultRate = roleDefaultRate;
	}

	public String getInsideIr35() {
		return insideIr35;
	}

	public void setInsideIr35(String insideIr35) {
		this.insideIr35 = insideIr35;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}