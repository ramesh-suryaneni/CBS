package com.imagination.cbs.service;

import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.TeamDto;

public interface TeamService {
	public Team storeTeamDetail(TeamDto teamDto);
}
