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
	
	private static final long serialVersionUID = -138503642699009642L;

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

	public void setDisciplineId(long discplineId) {
		this.disciplineId = discplineId;
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

}