/**
 * 
 */
package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.CurrencyDm;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Repository("currencyRepository")
public interface CurrencyRepository extends JpaRepository<CurrencyDm, Long> {

}
