/**
 * 
 */
package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

/**
 * @author Ramesh.Suryaneni
 *
 */

@Entity
@Table(name = "approver_override_jobs")
@Data
public class ApproverOverrides implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "override_id")
	private Long overrideId;
	
	@Column(name = "job_number")
	private String jobNumber;
	
	@Column(name = "employee_id")
	private Long employeeId;
	
	@Column(name = "changed_by")
	private String changedBy;

	@CreationTimestamp
	@Column(name = "changed_date")
	private Timestamp changedDate;

}
