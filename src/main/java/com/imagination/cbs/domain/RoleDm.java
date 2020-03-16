package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private long roleId;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "inside_ir35")
	private String insideIr35;

	@Column(name = "role_description")
	private String roleDescription;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "status")
	private String status;
	//
	// @Column(name = "discipline_id")
	// private Long disciplineId;

	@ManyToOne
	@JoinColumn(name = "discipline_id")
	private Discipline discipline;

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	// public Long getDisciplineId() {
	// return disciplineId;
	// }
	//
	// public void setDisciplineId(Long disciplineId) {
	// this.disciplineId = disciplineId;
	// }

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

	public String getInsideIr35() {
		return this.insideIr35;
	}

	public void setInsideIr35(String insideIr35) {
		this.insideIr35 = insideIr35;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}