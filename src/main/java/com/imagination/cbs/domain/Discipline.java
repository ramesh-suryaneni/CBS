package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the discipline database table.
 * 
 */
@Entity
@Table(name="discipline")
@NamedQuery(name="Discipline.findAll", query="SELECT d FROM Discipline d")
public class Discipline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="discipline_id")
	private long disciplineId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="discipline_description")
	private String disciplineDescription;

	@Column(name="discipline_name")
	private String disciplineName;

	//bi-directional one-to-one association to RoleDm
	@OneToOne(mappedBy="discipline")
	private RoleDm roleDm;

	public Discipline() {
	}

	public long getDisciplineId() {
		return this.disciplineId;
	}

	public void setDisciplineId(long disciplineId) {
		this.disciplineId = disciplineId;
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

	public RoleDm getRoleDm() {
		return this.roleDm;
	}

	public void setRoleDm(RoleDm roleDm) {
		this.roleDm = roleDm;
	}

}