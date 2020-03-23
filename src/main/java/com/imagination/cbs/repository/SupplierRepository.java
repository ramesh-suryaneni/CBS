package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagination.cbs.domain.SupplierTypeDm;

public interface SupplierRepository extends JpaRepository<SupplierTypeDm, Long> {

}
