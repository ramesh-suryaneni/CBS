package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.Region;

@Repository("regionRepository")
public interface RegionRepository extends JpaRepository<Region, Long> {

}
