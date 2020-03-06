package com.imagination.cbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Team;
import com.imagination.cbs.repository.TeamRepository;
import com.imagination.cbs.service.TeamService;

@Service("teamServiceImpl")
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Override
	public Team storeTeamDetail(Team team) {
		return teamRepository.save(team);
	}
}
