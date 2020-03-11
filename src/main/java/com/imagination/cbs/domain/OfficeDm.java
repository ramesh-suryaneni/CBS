package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="office_dm")
@NamedQuery(name="OfficeDm.findAll", query="SELECT o FROM OfficeDm o")
public class OfficeDm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="office_id")
	private long officeId;

	@Column(name="office_name")
	private String officeName;

	@Column(name="office_description")
	private String officeDescription;
	
	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private CountryDm countryDm;

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeDescription() {
		return officeDescription;
	}

	public void setOfficeDescription(String officeDescription) {
		this.officeDescription = officeDescription;
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

	public CountryDm getCountryDm() {
		return countryDm;
	}

	public void setCountryDm(CountryDm countryDm) {
		this.countryDm = countryDm;
	}

}
