/**
 * 
 */
package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.SupplierWorkLocationTypeDm;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Repository("supplierWorkLocationTypeRepository")
public interface SupplierWorkLocationTypeRepository extends JpaRepository<SupplierWorkLocationTypeDm, Long> {

}
