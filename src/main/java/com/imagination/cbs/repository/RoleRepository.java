/**
 * 
 */
package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.RoleDm;

/**
 * @author Ramesh.Suryaneni
 *
 */

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<RoleDm, Long> {

}
