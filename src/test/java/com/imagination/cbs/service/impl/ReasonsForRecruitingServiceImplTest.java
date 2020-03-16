package com.imagination.cbs.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.dto.ReasonsForRecruitingDto;
import com.imagination.cbs.mapper.ReasonsForRecruitingMapper;
import com.imagination.cbs.repository.ReasonsForRecruitingRepository;

@RunWith(MockitoJUnitRunner.class)
public class ReasonsForRecruitingServiceImplTest {

	@InjectMocks
	private ReasonsForRecruitingServiceImpl reasonsForRecruitingServiceImpl;
	
	@Mock
	private ReasonsForRecruitingRepository reasonsForRecruitingRepository;
	
	@Mock
	private ReasonsForRecruitingMapper reasonsForRecruitingMapper;
	
	@Test
	public void shouldReturnListOfReasonsForRecruiting() {
		
		List<ReasonsForRecruiting> listReasonsForRecruiting = createReasonsForRecruitingList();
		
		when(reasonsForRecruitingRepository.findAll()).thenReturn(listReasonsForRecruiting);
		when(reasonsForRecruitingMapper.toListOfReasonsForRecruitingDto(listReasonsForRecruiting)).thenReturn(createReasonsForRecruitingDtoList());
		
		List<ReasonsForRecruitingDto> actualListRecruitingDto = reasonsForRecruitingServiceImpl.getReasonsForRecruiting();
		
		assertEquals(1001l, actualListRecruitingDto.get(0).getReasonId());
		assertEquals("Internal resource not available", actualListRecruitingDto.get(0).getReasonName());
	}
	
	public List<ReasonsForRecruiting> createReasonsForRecruitingList()
	{
		List<ReasonsForRecruiting> listReasonsForRecruiting = new ArrayList<>();
		
		ReasonsForRecruiting recruiting = new ReasonsForRecruiting();
		recruiting.setReasonId(1001l);
		recruiting.setReasonName("Internal resource not available");
		
		listReasonsForRecruiting.add(recruiting);
		
		return listReasonsForRecruiting;
	}
	
	public List<ReasonsForRecruitingDto> createReasonsForRecruitingDtoList()
	{
		List<ReasonsForRecruitingDto> listReasonsForRecruitingDto = new ArrayList<>();
		
		ReasonsForRecruitingDto recruitingDto = new ReasonsForRecruitingDto();
		recruitingDto.setReasonId(1001l);
		recruitingDto.setReasonName("Internal resource not available");
	
		listReasonsForRecruitingDto.add(recruitingDto);
		
		return listReasonsForRecruitingDto;
	}

}
