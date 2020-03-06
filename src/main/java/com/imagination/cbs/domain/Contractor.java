package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the contractor database table.
 * 
 */
@Entity
@Table(name="contractor")
@NamedQuery(name="Contractor.findAll", query="SELECT c FROM Contractor c")
public class Contractor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="contractor_id")
	private long contractorId;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="company_type")
	private String companyType;

	@Column(name="contact_details")
	private String contactDetails;

	@Column(name="contractor_name")
	private String contractorName;

	private String status;

	//bi-directional many-to-one association to ContractorEmployee
	@OneToMany(mappedBy="contractor")
	private List<ContractorEmployee> contractorEmployees;

	public Contractor() {
	}

	public long getContractorId() {
		return this.contractorId;
	}

	public void setContractorId(long contractorId) {
		this.contractorId = contractorId;
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

	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getContactDetails() {
		return this.contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getContractorName() {
		return this.contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ContractorEmployee> getContractorEmployees() {
		return this.contractorEmployees;
	}

	public void setContractorEmployees(List<ContractorEmployee> contractorEmployees) {
		this.contractorEmployees = contractorEmployees;
	}

	public ContractorEmployee addContractorEmployee(ContractorEmployee contractorEmployee) {
		getContractorEmployees().add(contractorEmployee);
		contractorEmployee.setContractor(this);

		return contractorEmployee;
	}

	public ContractorEmployee removeContractorEmployee(ContractorEmployee contractorEmployee) {
		getContractorEmployees().remove(contractorEmployee);
		contractorEmployee.setContractor(null);

		return contractorEmployee;
	}

}