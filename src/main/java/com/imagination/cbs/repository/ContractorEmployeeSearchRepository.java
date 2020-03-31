package com.imagination.cbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ContractorEmployeeSearch;

@Repository("contractorEmployeeSearchRepository")
public interface ContractorEmployeeSearchRepository extends JpaRepository<ContractorEmployeeSearch, Long>{

	Page<ContractorEmployeeSearch> findByRoleId(long roleId, Pageable pageable);

	Page<ContractorEmployeeSearch> findByRoleIdAndContractorEmployeeNameContains(long roleId, String contractorName, Pageable pageable);

}
