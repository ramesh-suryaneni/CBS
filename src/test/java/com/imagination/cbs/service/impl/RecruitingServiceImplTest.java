package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.ReasonsForRecruiting;
import com.imagination.cbs.dto.RecruitingDto;
import com.imagination.cbs.mapper.RecruitingMapper;
import com.imagination.cbs.repository.RecruitingRepository;
import com.imagination.cbs.service.RecruitingService;

@RunWith(MockitoJUnitRunner.class)
public class RecruitingServiceImplTest {

	@InjectMocks
	private RecruitingService recruitingService;
	@Mock
	private RecruitingRepository recruitingRepository;

	@Mock
	private RecruitingMapper recruitingMapper;

	@Test
	@Ignore
	public void shouldReturnListOfReasonsForRecruiting() {

		when(recruitingRepository.findAll()).thenReturn(getListOfReasonsForRecruiting());
		when(recruitingMapper.toListRecruitingDto(getListOfReasonsForRecruiting()))
				.thenReturn(getListOfRecruitingDto());

		List<RecruitingDto> actualListOfRecruitingDto = recruitingService.getAllReasonForRecruiting();
		assertEquals(2L, actualListOfRecruitingDto.get(0).getReasonId());
		assertEquals("Specific skills required", actualListOfRecruitingDto.get(0).getReasonName());
		assertEquals("Specific skills required", actualListOfRecruitingDto.get(0).getReasonDescription());

	}

	private List<ReasonsForRecruiting> getListOfReasonsForRecruiting() {

		List<ReasonsForRecruiting> list = new ArrayList<>();

		ReasonsForRecruiting recruiting = new ReasonsForRecruiting();
		recruiting.setReasonId(2L);
		recruiting.setReasonName("Specific skills required");
		recruiting.setReasonDescription("Specific skills required");

		list.add(recruiting);

		return list;
	}

	private List<RecruitingDto> getListOfRecruitingDto() {

		RecruitingDto recruitingDto = new RecruitingDto();
		List<RecruitingDto> listOfRecruitingDto = new ArrayList<>();

		recruitingDto.setReasonId(2L);
		recruitingDto.setReasonName("Specific skills required");
		recruitingDto.setReasonDescription("Specific skills required");

		listOfRecruitingDto.add(recruitingDto);

		return listOfRecruitingDto;

	}

}
