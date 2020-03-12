package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the currency_dm database table.
 * 
 */
@Entity
@Table(name = "currency_dm")
@NamedQuery(name = "CurrencyDm.findAll", query = "SELECT c FROM CurrencyDm c")
public class CurrencyDm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "currency_id")
	private Long currencyId;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "contractor_number")
	private Long contractorNumber;

	// bi-directional one-to-one association to BookingRevision
	@OneToOne
	@JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
	private BookingRevision bookingRevision;

	public CurrencyDm() {
	}

	public Long getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getContractorNumber() {
		return this.contractorNumber;
	}

	public void setContractorNumber(Long contractorNumber) {
		this.contractorNumber = contractorNumber;
	}

	public BookingRevision getBookingRevision() {
		return this.bookingRevision;
	}

	public void setBookingRevision(BookingRevision bookingRevision) {
		this.bookingRevision = bookingRevision;
	}

}