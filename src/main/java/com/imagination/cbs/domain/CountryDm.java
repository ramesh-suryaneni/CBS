package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="country_dm")
@NamedQuery(name="CountryDm.findAll", query="SELECT c FROM CountryDm c")
public class CountryDm implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="country_id")
	private long countryId;

	@Column(name="country_name")
	private String countryName;

	@Column(name="country_description")
	private String countryDescription;
	
	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@OneToMany(mappedBy="countryDm")
	private List<OfficeDm> officeDms;
	
	
	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryDescription() {
		return countryDescription;
	}

	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public List<OfficeDm> getOfficeDms() {
		return officeDms;
	}

	public void setOfficeDms(List<OfficeDm> officeDms) {
		this.officeDms = officeDms;
	}
}
