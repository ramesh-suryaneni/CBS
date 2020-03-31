package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.SupplierTypeDm;

@Repository("supplierRepository")
public interface SupplierTypeRepository extends JpaRepository<SupplierTypeDm, Long> {

}
