package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.ReasonsForRecruiting;

@Repository("reasonsForRecruitingRepository")
public interface ReasonsForRecruitingRepository extends JpaRepository<ReasonsForRecruiting, Long>{

}
