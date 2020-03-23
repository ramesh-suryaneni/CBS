package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imagination.cbs.domain.Team;

/**
 * @author Pravin.Budage
 *
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
	
	@Query("FROM Team WHERE team_name = ?1")
	Team findByTeamName(String name);
}
