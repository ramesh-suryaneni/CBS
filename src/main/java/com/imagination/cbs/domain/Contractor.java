package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;


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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="contractor_id")
	private long contractorId;

	@Column(name="contractor_name")
	private String contractorName;

	@Column(name="company_type")
	private String companyType;

	@Column(name="contact_details")
	private String contactDetails;

	@CreationTimestamp
	@Column(name="changed_date")
	private Timestamp changedDate;

	@Column(name="changed_by")
	private String changedBy;

	@Column(name="status")
	private String status;

	@Column(name="maconomy_vendor_number")
	private String maconomyVendorNumber;
	
	@Column(name="address_line1")
	private String addressLine1;
	
	@Column(name="address_line2")
	private String addresLine2;
	
	@Column(name="address_line3")
	private String addresLine3;
	
	@Column(name="postal_district")
	private String postalDistrict;
	
	@Column(name="postal_code")
	private String postalCode;

	@Column(name="country")
	private String country;
	
	@Column(name="attention")
	private String attention;
	
	@Column(name="email")
	private String email;
	
	@Column(name="on_preferred_supplier_list")
	private String onPreferredSupplierList;
	
	@OneToMany(mappedBy = "contractor")
	private List<ContractorEmployee> contractorEmployeeList;
	
	public Contractor() {
	}

	public long getContractorId() {
		return contractorId;
	}

	public void setContractorId(long contractorId) {
		this.contractorId = contractorId;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public Timestamp getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaconomyVendorNumber() {
		return maconomyVendorNumber;
	}

	public void setMaconomyVendorNumber(String maconomyVendorNumber) {
		this.maconomyVendorNumber = maconomyVendorNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddresLine2() {
		return addresLine2;
	}

	public void setAddresLine2(String addresLine2) {
		this.addresLine2 = addresLine2;
	}

	public String getAddresLine3() {
		return addresLine3;
	}

	public void setAddresLine3(String addresLine3) {
		this.addresLine3 = addresLine3;
	}

	public String getPostalDistrict() {
		return postalDistrict;
	}

	public void setPostalDistrict(String postalDistrict) {
		this.postalDistrict = postalDistrict;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOnPreferredSupplierList() {
		return onPreferredSupplierList;
	}

	public void setOnPreferredSupplierList(String onPreferredSupplierList) {
		this.onPreferredSupplierList = onPreferredSupplierList;
	}

	public List<ContractorEmployee> getContractorEmployeeList() {
		return contractorEmployeeList;
	}

	public void setContractorEmployeeList(List<ContractorEmployee> contractorEmployeeList) {
		this.contractorEmployeeList = contractorEmployeeList;
	}

}