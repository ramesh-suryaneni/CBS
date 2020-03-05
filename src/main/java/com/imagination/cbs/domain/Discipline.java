package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the discipline database table.
 * 
 */
@Entity
@Table(name="discipline")
@NamedQuery(name="Discipline.findAll", query="SELECT d FROM Discipline d")
public class Discipline implements Serializable {
	
	private static final long serialVersionUID = 366417481431559168L;

	@Id
	@Column(name="discipline_id")
	private long disciplineId;

	@Column(name="discipline_description")
	private String disciplineDescription;

	@Column(name="discipline_name")
	private String disciplineName;

	public Discipline() {
	}

	public long getDisciplineId() {
		return this.disciplineId;
	}

	public void setDisciplineId(long disciplineId) {
		this.disciplineId = disciplineId;
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