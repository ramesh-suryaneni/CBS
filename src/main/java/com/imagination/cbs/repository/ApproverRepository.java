/**
 * 
 */
package com.imagination.cbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.Approver;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Repository
public interface ApproverRepository extends JpaRepository<Approver, Long> {
	
	public List<Approver> findAllByTeamId(Long teamId);

}
