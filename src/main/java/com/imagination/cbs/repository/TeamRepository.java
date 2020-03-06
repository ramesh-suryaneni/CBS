package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagination.cbs.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	public Team save(Team team);
}
