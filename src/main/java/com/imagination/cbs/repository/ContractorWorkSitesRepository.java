package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ContractorWorkSite;

@Repository("contractorWorkSites")
public interface ContractorWorkSitesRepository extends JpaRepository<ContractorWorkSite, Long> {

}
