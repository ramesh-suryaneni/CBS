package com.imagination.cbs.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.dto.ReasonsForRecruitingDto;

@Mapper(componentModel = "spring")
public interface ReasonsForRecruitingMapper {

	public List<ReasonsForRecruitingDto> toListOfReasonsForRecruitingDto(List<ReasonsForRecruiting> listOfReasonForRecruiting); 

}
