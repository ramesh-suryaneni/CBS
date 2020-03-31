/**
 * 
 */
package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.RoleDefaultRate;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Repository("roleDefaultRateRepository")
public interface RoleDefaultRateRepository extends JpaRepository<RoleDefaultRate, Long> {

	public RoleDefaultRate findByDefaultRateId(Long roleId);

}
