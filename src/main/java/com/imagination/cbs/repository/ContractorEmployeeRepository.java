package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ContractorEmployee;

@Repository("contractorEmployeeRepository")
public interface ContractorEmployeeRepository extends JpaRepository<ContractorEmployee, Long> {

	@Query("SELECT c FROM ContractorEmployee c WHERE c.contractorEmployeeId= :employeeId AND c.contractor.contractorId=:contractorId")
	public ContractorEmployee findContractorEmployeeByContractorIdAndEmployeeId( @Param("contractorId")Long contractorId,@Param("employeeId")Long employeeId);
}
