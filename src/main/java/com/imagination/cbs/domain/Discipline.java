package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the discipline database table.
 * 
 */
@Entity
@Table(name = "discipline")
@NamedQuery(name = "Discipline.findAll", query = "SELECT d FROM Discipline d")
public class Discipline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "discipline_id")
	private Long disciplineId;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "discipline_description")
	private String disciplineDescription;

	@Column(name = "discipline_name")
	private String disciplineName;

	// bi-directional one-to-one association to RoleDm
	@OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL)
	private List<RoleDm> roles;

	public Discipline() {
	}

	public Long getDisciplineId() {
		return this.disciplineId;
	}

	public void setDisciplineId(Long disciplineId) {
		this.disciplineId = disciplineId;
	}

	public String getChangedBy() {
		return this.changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public List<RoleDm> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDm> roles) {
		this.roles = roles;
	}

	public Timestamp getChangedDate() {
		return this.changedDate;
	}

	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
	}

	public String getDisciplineDescription() {
		return this.disciplineDescription;
	}

	public void setDisciplineDescription(String disciplineDescription) {
		this.disciplineDescription = disciplineDescription;
	}

	public String getDisciplineName() {
		return this.disciplineName;
	}

	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
	}
}