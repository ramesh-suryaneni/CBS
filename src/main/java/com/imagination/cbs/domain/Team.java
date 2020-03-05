package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the team database table.
 * 
 */
@Entity
@Table(name="team")
@NamedQuery(name="Team.findAll", query="SELECT t FROM Team t")
public class Team implements Serializable {

	private static final long serialVersionUID = -5821399418314708931L;

	@Id
	@Column(name="team_id")
	private long teamId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="team_name")
	private String teamName;

	public Team() {
	}

	public long getTeamId() {
		return this.teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
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

	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

}