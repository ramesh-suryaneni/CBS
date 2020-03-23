package com.imagination.cbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.imagination.cbs.domain.ContractorIndex;

public interface ContractorIndexRepository extends JpaRepository<ContractorIndex, Long> {

	Page<ContractorIndex> findByContractorNameContains(String contractorName, Pageable pageable);
	
}
