package com.imagination.cbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.Contractor;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long>{
	
	Page<Contractor> findAll(Pageable pageable);

	Page<Contractor> findByContractorNameContains(String contractorName, Pageable pageable);

}
