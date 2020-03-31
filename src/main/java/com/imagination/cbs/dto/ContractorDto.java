package com.imagination.cbs.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ContractorDto {

	private long contractorId;
	private String contractorName;
	private String companyType;
	private String contactDetails;
	private Timestamp changedDate;
	private String changedBy;
	private String status;
	private String maconomyVendorNumber;
	private String addressLine1;
	private String addresLine2;
	private String addresLine3;
	private String postalDistrict;
	private String postalCode;
	private String country;
	private String attention;
	private String email;
	private String onPreferredSupplierList;
	
	@Override
	public String toString() {
		return "ContractorDto [contractorId=" + contractorId + ", contractorName=" + contractorName + "]";
	}
	
}
