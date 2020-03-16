package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.domain.Permission;
/**
 * The persistent class for the employee_permissions database table.
 * 
 */
@Entity
@Table(name="employee_permissions")
@NamedQuery(name="EmployeePermissions.findAll", query="SELECT e from EmployeePermissions e")
public class EmployeePermissions implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_permission_id")
	private long empPermissionId;
	
	@Column(name="changed_date")
	private Timestamp changedDate;
	
	@Column(name="changed_by")
	private String changedBy;

	@OneToOne
	@JoinColumn(name = "company_number")
	private Company company;
	
	@OneToOne
	@JoinColumn(name = "employee_id")
	private EmployeeMapping employeeMapping;
	
	@OneToOne
	@JoinColumn(name="permission_id")
	private Permission permission;

	public long getEmpPermissionId() {
		return empPermissionId;
	}

	public void setEmpPermissionId(long empPermissionId) {
		this.empPermissionId = empPermissionId;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public EmployeeMapping getEmployeeMapping() {
		return employeeMapping;
	}

	public void setEmployeeMapping(EmployeeMapping employeeMapping) {
		this.employeeMapping = employeeMapping;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
}
