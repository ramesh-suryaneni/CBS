package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the office_dm database table.
 * 
 */
@Entity
@Table(name="office_dm")
@NamedQuery(name="OfficeDm.findAll", query="SELECT o FROM OfficeDm o")
public class OfficeDm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="office_id")
	private long officeId;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="inside_ir35")
	private String insideIr35;

	@Column(name="office_description")
	private String officeDescription;

	@Column(name="office_name")
	private String officeName;

	public OfficeDm() {
	}

	public long getOfficeId() {
		return this.officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getInsideIr35() {
		return this.insideIr35;
	}

	public void setInsideIr35(String insideIr35) {
		this.insideIr35 = insideIr35;
	}

	public String getOfficeDescription() {
		return this.officeDescription;
	}

	public void setOfficeDescription(String officeDescription) {
		this.officeDescription = officeDescription;
	}

	public String getOfficeName() {
		return this.officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

}