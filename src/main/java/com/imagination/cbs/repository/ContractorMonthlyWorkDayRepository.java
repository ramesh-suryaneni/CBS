package com.imagination.cbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.imagination.cbs.domain.ContractorMonthlyWorkDay;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface ContractorMonthlyWorkDayRepository extends JpaRepository<ContractorMonthlyWorkDay, Long> {
	List<ContractorMonthlyWorkDay> findByBookingRevisionId(@Param("bookingRevisionId") Long bookingRevisionId);
}
