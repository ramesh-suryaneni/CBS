package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the role_default_rate database table.
 * 
 */
@Entity
@Table(name="role_default_rate")
@NamedQuery(name="RoleDefaultRate.findAll", query="SELECT r FROM RoleDefaultRate r")
public class RoleDefaultRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="default_rate_id")
	private long defaultRateId;

	@Column(name="currency_id")
	private long currencyId;

	@Column(name="date_from")
	private Timestamp dateFrom;

	private BigDecimal rate;

	//bi-directional one-to-one association to RoleDm
	@OneToOne(mappedBy="roleDefaultRate")
	private RoleDm roleDm;

	public RoleDefaultRate() {
	}

	public long getDefaultRateId() {
		return this.defaultRateId;
	}

	public void setDefaultRateId(long defaultRateId) {
		this.defaultRateId = defaultRateId;
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

	public RoleDm getRoleDm() {
		return this.roleDm;
	}

	public void setRoleDm(RoleDm roleDm) {
		this.roleDm = roleDm;
	}

}