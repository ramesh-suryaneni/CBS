package com.imagination.cbs.domain;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contractor_employee_search")
public class ContractorEmployeeSearch {

	@Id
	@Column(name="row_no")
	private Long rowNo; 

	@Column(name="contractor_employee_id")
	private Long contractorEmployeeId;

	@Column(name="employee_name")
	private String contractorEmployeeName;

	@Column(name="rate")
	private BigDecimal dayRate;

	@Column(name="role_id")
	private Long roleId;

	@Column(name="role_name")
	private String role;

	@Column(name="contractor_id")
	private Long contractorId;

	@Column(name="contractor_name")
	private String company;

	@Column(name="no_of_bookings")
	private Integer noOfBookingsInPast;

	public Long getRowNo() {
		return rowNo;
	}

	public void setRowNo(Long rowNo) {
		this.rowNo = rowNo;
	}

	public Long getContractorEmployeeId() {
		return contractorEmployeeId;
	}

	public void setContractorEmployeeId(Long contractorEmployeeId) {
		this.contractorEmployeeId = contractorEmployeeId;
	}

	public String getContractorEmployeeName() {
		return contractorEmployeeName;
	}

	public void setContractorEmployeeName(String contractorEmployeeName) {
		this.contractorEmployeeName = contractorEmployeeName;
	}

	public BigDecimal getDayRate() {
		return dayRate;
	}

	public void setDayRate(BigDecimal dayRate) {
		this.dayRate = dayRate;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getNoOfBookingsInPast() {
		return noOfBookingsInPast;
	}

	public void setNoOfBookingsInPast(Integer noOfBookingsInPast) {
		this.noOfBookingsInPast = noOfBookingsInPast;
	}

}