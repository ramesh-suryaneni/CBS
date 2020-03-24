package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ReasonsForRecruiting;

@Repository("recruitingRepository")
public interface RecruitingRepository extends JpaRepository<ReasonsForRecruiting, Long> {

}
