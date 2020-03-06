/**
 * 
 */
package com.imagination.cbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.RoleDm;

/**
 * @author Ramesh.Suryaneni
 *
 */

@Repository("bookingRepository")
public interface RoleRepository extends JpaRepository<RoleDm, Long> {
	
	@Query(value = "SELECT RD FROM RoleDm RD WHERE RD.disciplineId =:disciplineId")
	List<RoleDm> findRoleByDisciplineId(@Param("disciplineId") Long disciplineId);
	
	
	

}
