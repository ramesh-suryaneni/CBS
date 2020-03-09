package com.imagination.cbs.mapper;

import java.sql.Timestamp;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.imagination.cbs.domain.Team;
import com.imagination.cbs.dto.TeamDto;

@Mapper(componentModel = "spring")
public interface TeamMapper {
	TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

	@Mapping(source = "changedDate", target = "changedDate", qualifiedByName = "stringToTimeStamp")
	public Team toTeamDomain(TeamDto teamDto);

	@Named("stringToTimeStamp")
	public static Timestamp stringToTimeStampConverter(String date) {
		return Timestamp.valueOf(date);
	}
}
