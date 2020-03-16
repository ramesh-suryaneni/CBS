package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.imagination.cbs.domain.CurrencyDm;
import com.imagination.cbs.domain.Region;
/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@Table(name="company")
@NamedQuery(name="Company.findAll", query="SELECT c from Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="company_number")
	private long conpanyNumber;
	
	@Column(name = "company_description")
	private String companyDescription;
	
	@Column(name="changed_date")
	private Timestamp changedDate;
	
	@Column(name="changed_by")
	private String changedBy;

	@OneToOne
	@JoinColumn(name="currency_id")
	private CurrencyDm currencyDm;
	
	@OneToOne
	@JoinColumn(name = "region_id")
	private Region region;

	public long getConpanyNumber() {
		return conpanyNumber;
	}

	public void setConpanyNumber(long conpanyNumber) {
		this.conpanyNumber = conpanyNumber;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
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

	public CurrencyDm getCurrencyDm() {
		return currencyDm;
	}

	public void setCurrencyDm(CurrencyDm currencyDm) {
		this.currencyDm = currencyDm;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
	
}
