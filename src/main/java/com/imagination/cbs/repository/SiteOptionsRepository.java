/**
 * 
 */
package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.SiteOptions;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Repository("siteOptionsRepository")
public interface SiteOptionsRepository extends JpaRepository<SiteOptions, Long> {

}
