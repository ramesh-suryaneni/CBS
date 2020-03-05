package com.imagination.cbs.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.imagination.cbs.domain.RoleDm;

public interface RoleRepository extends Repository<RoleDm, Long> {

	List<RoleDm> findByDisciplineId(long disciplineId);
}
