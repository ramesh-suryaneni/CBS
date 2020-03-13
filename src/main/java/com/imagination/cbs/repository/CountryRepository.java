package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.imagination.cbs.domain.CountryDm;


@Repository("countryRepository")
public interface CountryRepository extends JpaRepository<CountryDm, Long> {

	
}
