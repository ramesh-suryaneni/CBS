package com.imagination.cbs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author pravin.budage
 *
 */
@Entity
@Table(name = "contractor_work_sites")
public class ContractorWorkSite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "booking_revision_id")
	private BookingRevision bookingRevision;

	@OneToOne
	@JoinColumn(name = "site_id")
	private SiteOptions siteOptions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BookingRevision getBookingRevision() {
		return bookingRevision;
	}

	public void setBookingRevision(BookingRevision bookingRevision) {
		this.bookingRevision = bookingRevision;
	}

	public SiteOptions getSiteOptions() {
		return siteOptions;
	}

	public void setSiteOptions(SiteOptions siteOptions) {
		this.siteOptions = siteOptions;
	}
}
