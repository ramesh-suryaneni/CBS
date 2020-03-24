package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagination.cbs.domain.Team;

/**
 * @author Pravin.Budage
 *
 */
public interface TeamRepository extends JpaRepository<Team, Long> {

	Team findByTeamName(String name);
}
