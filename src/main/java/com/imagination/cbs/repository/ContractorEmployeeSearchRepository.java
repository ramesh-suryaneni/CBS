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

	Page<ContractorEmployeeSearch> findByRoleContainingIgnoreCase(String roleName, Pageable pageable);

	Page<ContractorEmployeeSearch> findByContractorEmployeeNameContainingIgnoreCase(String contractorEmployeeName, Pageable pageable);

	@Query("SELECT ce FROM ContractorEmployeeSearch ce WHERE LOWER(ce.contractorEmployeeName) LIKE LOWER(concat('%', :contractorEmpName, '%')) AND LOWER(ce.role) LIKE LOWER(concat('%', :roleName, '%'))")
	Page<ContractorEmployeeSearch> findByContractorEmployeeNameAndRoleName(@Param("contractorEmpName") String contractorEmpName, @Param("roleName") String roleName, Pageable pageable);
}
