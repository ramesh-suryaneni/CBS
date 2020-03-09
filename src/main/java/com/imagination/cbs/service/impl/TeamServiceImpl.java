package com.imagination.cbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.TeamDto;
import com.imagination.cbs.mapper.TeamMapper;
import com.imagination.cbs.repository.TeamRepository;
import com.imagination.cbs.service.TeamService;

@Service("teamServiceImpl")
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private TeamMapper teamMapper;

	@Override
	public Team storeTeamDetail(TeamDto team) {
		return teamRepository.save(teamMapper.toTeamDomain(team));
	}
}
