/**
 * 
 */
package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ApproverOverrides;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Repository
public interface ApproverOverridesRepository extends JpaRepository<ApproverOverrides, Long> {
	
	public ApproverOverrides findByEmployeeIdAndJobNumber(Long employeeId, String jobNumber);

}
