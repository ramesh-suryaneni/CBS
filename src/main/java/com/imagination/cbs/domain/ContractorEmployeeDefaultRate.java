package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the contractor_employee_default_rate database table.
 * 
 */
@Entity
@Table(name="contractor_employee_default_rate")
@NamedQuery(name="ContractorEmployeeDefaultRate.findAll", query="SELECT c FROM ContractorEmployeeDefaultRate c")
public class ContractorEmployeeDefaultRate implements Serializable {
	
	private static final long serialVersionUID = -2530135001767561726L;

	@Id
	@Column(name="rate_id")
	private long rateId;

	@Column(name="contractor_employee_id")
	private long contractorEmployeeId;

	@Column(name="currency_id")
	private long currencyId;

	@Column(name="date_from")
	private Timestamp dateFrom;

	private BigDecimal rate;

	public ContractorEmployeeDefaultRate() {
	}

	public long getRateId() {
		return this.rateId;
	}

	public void setRateId(long rateId) {
		this.rateId = rateId;
	}

	public long getContractorEmployeeId() {
		return this.contractorEmployeeId;
	}

	public void setContractorEmployeeId(long contractorEmployeeId) {
		this.contractorEmployeeId = contractorEmployeeId;
	}

	public long getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	public Timestamp getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}