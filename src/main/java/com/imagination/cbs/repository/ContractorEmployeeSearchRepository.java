package com.imagination.cbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ContractorEmployeeSearch;

@Repository("contractorEmployeeSearchRepository")
public interface ContractorEmployeeSearchRepository extends JpaRepository<ContractorEmployeeSearch, Long>{

	@Query("SELECT ce FROM ContractorEmployeeSearch ce WHERE ce.contractorEmployeeName LIKE %:contractorEmpName% OR ce.role LIKE %:roleName%")
	Page<ContractorEmployeeSearch> findByContractorEmployeeNameOrRoleNameContains(@Param("contractorEmpName") String contractorEmpName, @Param("roleName") String roleName, Pageable pageable);
}
