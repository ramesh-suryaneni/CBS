package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.Team;

/**
 * @author Pravin.Budage
 *
 */
@Repository("teamRepository")
public interface TeamRepository extends JpaRepository<Team, Long> {

	Team findByTeamName(String name);
}
