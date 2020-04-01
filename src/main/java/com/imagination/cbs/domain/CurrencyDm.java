package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the currency_dm database table.
 * 
 */
@Entity
@Table(name = "currency_dm")
public class CurrencyDm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "currency_id")
	private Long currencyId;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "currency_code")
	private String currencyCode;

	@Column(name = "currency_name")
	private String currencyName;

	public CurrencyDm() {
	}

	public Long getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
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

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
}