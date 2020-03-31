package com.imagination.cbs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.dto.RecruitingDto;

@Mapper(componentModel = "spring")
public interface RecruitingMapper {

	public List<RecruitingDto> toListRecruitingDto(List<ReasonsForRecruiting> listOfRecruiting);

	public RecruitingDto toRecruitingDtoFromReasonsForRecruitingDomain(ReasonsForRecruiting reasonsForRecruiting);

}
