package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the role_dm database table.
 * 
 */
@Entity
@Table(name="role_dm")
@NamedQuery(name="RoleDm.findAll", query="SELECT r FROM RoleDm r")
public class RoleDm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id")
	private long roleId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="discipline_id")
	private long disciplineId;

	@Column(name="role_description")
	private String roleDescription;

	@Column(name="role_name")
	private String roleName;

	//bi-directional one-to-one association to ContractorEmployeeRole
	@OneToOne(mappedBy="roleDm")
	private ContractorEmployeeRole contractorEmployeeRole;

	//bi-directional one-to-one association to RoleDefaultRate
	@OneToOne
	@JoinColumn(name="role_id", referencedColumnName="role_id")
	private RoleDefaultRate roleDefaultRate;

	public RoleDm() {
	}

	public long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(long roleId) {
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

	public long getDisciplineId() {
		return this.disciplineId;
	}

	public void setDisciplineId(long disciplineId) {
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

}