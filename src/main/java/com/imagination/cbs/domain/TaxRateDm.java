package com.imagination.cbs.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the tax_rate_dm database table.
 * 
 */
@Entity
@Table(name="tax_rate_dm")
@NamedQuery(name="TaxRateDm.findAll", query="SELECT t FROM TaxRateDm t")
public class TaxRateDm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="rate_percent")
	private BigDecimal ratePercent;

	@Column(name="tax_description")
	private String taxDescription;

	public TaxRateDm() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getRatePercent() {
		return this.ratePercent;
	}

	public void setRatePercent(BigDecimal ratePercent) {
		this.ratePercent = ratePercent;
	}

	public String getTaxDescription() {
		return this.taxDescription;
	}

	public void setTaxDescription(String taxDescription) {
		this.taxDescription = taxDescription;
	}

}