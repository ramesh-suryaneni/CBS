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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="default_rate_id")
	private long defaultRateId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="date_from")
	private Timestamp dateFrom;

	private BigDecimal rate;

	@Column(name="role_id")
	private long roleId;
	
	@Column(name="currency_id")
	private long currencyId;
	
	/*
	//bi-directional one-to-one association to RoleDm
	@OneToOne
	@JoinColumn(name="role_id")
	private RoleDm roleDm;

	//bi-directional one-to-one association to CurrencyDm
	@OneToOne
	@JoinColumn(name="currency_id")
	private CurrencyDm currencyDm;
	 */
	
	public RoleDefaultRate() {
	}

	public long getDefaultRateId() {
		return this.defaultRateId;
	}

	public void setDefaultRateId(long defaultRateId) {
		this.defaultRateId = defaultRateId;
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

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

/*
 	public RoleDm getRoleDm() {
		return this.roleDm;
	}

	public void setRoleDm(RoleDm roleDm) {
		this.roleDm = roleDm;
	}

	public CurrencyDm getCurrencyDm() {
		return this.currencyDm;
	}

	public void setCurrencyDm(CurrencyDm currencyDm) {
		this.currencyDm = currencyDm;
	}
*/
}