package com.imagination.cbs.domain;

import javax.persistence.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.persistence.Table;

import com.imagination.cbs.domain.Team;
/**
 * The persistent class for the active_company_teams database table.
 * 
 */
@Entity
@Table(name="active_company_teams")
@NamedQuery(name="ActiveCompanyTeams.findAll", query="SELECT a FROM ActiveCompanyTeams a")
public class ActiveCompanyTeams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long activeCompanyTeamId;
	
	@Column(name="changed_date")
	private Timestamp changedDate;
	
	@Column(name="changed_by")
	private String changedBy;

	@OneToOne
	@JoinColumn(name="company_number")
	private Company company;
	
	@OneToOne
	@JoinColumn(name="team_id")
	private Team team;

	public long getActiveCompanyTeamId() {
		return activeCompanyTeamId;
	}

	public void setActiveCompanyTeamId(long activeCompanyTeamId) {
		this.activeCompanyTeamId = activeCompanyTeamId;
	}

	public Timestamp getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
}
