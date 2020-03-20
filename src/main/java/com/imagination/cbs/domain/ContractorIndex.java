package com.imagination.cbs.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name="contractor_index_view")
public class ContractorIndex {
	
	@Id
	@Column(name="contractor_index_id")
	private long contractorIndexId;
	
	//private String supplierName;
	
	@Column(name="contractor_name")
	private String contractorName;
	
	@Column(name="known_as")
	private String alias;
	
	@Column(name="discipline_name")
	private String discipline;
	
	@Column(name="role_name")
	private String role;
	
	@Column(name="rate")
	private BigDecimal rate;
	
	//private String latestProject;
	
	@Column(name="rating_given")
	private BigDecimal rating;

	//private String comments;
	
	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

}
