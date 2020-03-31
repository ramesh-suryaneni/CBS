package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ContractorEmployee;

@Repository("contractorEmployeeRepository")
public interface ContractorEmployeeRepository extends JpaRepository<ContractorEmployee, Long> {

}
