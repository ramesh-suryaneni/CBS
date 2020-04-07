/**
 * 
 */
package com.imagination.cbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.Approver;
import com.imagination.cbs.domain.EmployeeMapping;
import com.imagination.cbs.domain.Team;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Repository
public interface ApproverRepository extends JpaRepository<Approver, Long> {
	
	public List<Approver> findAllByTeamId(Long teamId);
	
	Approver findByTeamAndEmployeeAndApproverOrder(Team team, EmployeeMapping employee, Long order);

}
