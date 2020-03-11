package com.imagination.cbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.Contractor;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long>{

	List<Contractor> findByContractorNameContains(String contractorName);
}
