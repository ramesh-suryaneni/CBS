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
@Table(name = "role_default_rate")
public class RoleDefaultRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "default_rate_id")
	private Long defaultRateId;

	@Column(name = "changed_by")
	private String changedBy;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "date_from")
	private Timestamp dateFrom;

	private BigDecimal rate;

	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "currency_id")
	private Long currencyId;

	public RoleDefaultRate() {
	}

	public Long getDefaultRateId() {
		return this.defaultRateId;
	}

	public void setDefaultRateId(Long defaultRateId) {
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

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the currencyId
	 */
	public Long getCurrencyId() {
		return currencyId;
	}

	/**
	 * @param currencyId the currencyId to set
	 */
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	

}