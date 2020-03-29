package com.imagination.cbs.mapper;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.TeamDto;

/**
 * @author pravin.budage
 *
 */
@Mapper(componentModel = "spring")
public interface TeamMapper {

	public TeamDto toTeamDtoFromTeamDomain(Team team);
}
