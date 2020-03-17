package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the access_rights_tbc database table.
 * 
 */
@Entity
@Table(name="employee_permissions")
public class EmployeePermissions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_permission_id")
	private Long empPermissionId;

	@Column(name="employee_id")
	private Long employeeId;
	
	@Column(name="company_number")
	private Long companyNumber;
	
	@Column(name="permission_id")
	private Long permissionId;
	
	@Column(name="changed_date")
	private Timestamp changedDate;
	
	@Column(name="changed_by")
	private String changedBy;

	

}