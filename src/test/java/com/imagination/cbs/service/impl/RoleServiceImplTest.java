/**
 * 
 */
package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import com.imagination.cbs.domain.RoleDefaultRate;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.dto.RoleDto;
import com.imagination.cbs.mapper.RoleMapper;
import com.imagination.cbs.repository.RoleDefaultRateRepository;
import com.imagination.cbs.repository.RoleRepository;

/**
 * @author pappu.rout
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

	@InjectMocks
	private RoleServiceImpl roleServiceImpl;

	@Mock
	RoleRepository roleRepository;

	@Mock
	RoleDefaultRateRepository roleDefaultRateRepository;

	@Mock
	RoleMapper roleMapper;

	@Value("${cest_outcome.inside_ir35_pdf}")
	private String insideIR35CestPDF;

	@Value("${cest_outcome.outside_ir35_pdf}")
	private String outsideIR35CestPDF;

	@Test
	public void shouldReturnCESToutcome() {

		Optional<RoleDm> roleDm = Optional.ofNullable(createRole());
		when(roleRepository.findById(100L)).thenReturn(roleDm);
		when(roleMapper.toRoleDTO(roleDm.get())).thenReturn(getContractorRoleDto());

		RoleDto actualListOfContractorRoleDto = roleServiceImpl.getCESToutcome(100L);
		assertEquals("2", actualListOfContractorRoleDto.getRoleId());
		assertEquals("Logistics Manager", actualListOfContractorRoleDto.getRoleName());

	}
	
	@Test
	public void shouldReturnNullWhenRoleIsNotPresentInDBForProvidedRoleId() {

		Optional<RoleDm> roleDm = Optional.empty();
		when(roleRepository.findById(1234L)).thenReturn(roleDm);

		RoleDto actual = roleServiceImpl.getCESToutcome(1234L);
		assertEquals(null, actual);

	}
	
	
	@Test
	public void shouldReturnCESToutcomeFromInsideIR35CestPDF() {
		
		RoleDm roleDm = createRole();
		roleDm.setInsideIr35("true");
		RoleDto contractorRoleDto = getContractorRoleDto();
		contractorRoleDto.setInsideIr35("true");
		Optional<RoleDm> roleDmList = Optional.ofNullable(roleDm);
		List<RoleDefaultRate> roleDetaultRateList = new ArrayList<>();
		
		when(roleRepository.findById(100L)).thenReturn(roleDmList);
		when(roleMapper.toRoleDTO(roleDmList.get())).thenReturn(contractorRoleDto);
		when(roleDefaultRateRepository.findAllByRoleId(100L)).thenReturn(roleDetaultRateList);

		RoleDto actualListOfContractorRoleDto = roleServiceImpl.getCESToutcome(100L);
		
		assertEquals("2", actualListOfContractorRoleDto.getRoleId());
		assertEquals("Logistics Manager", actualListOfContractorRoleDto.getRoleName());
		assertEquals("insideIR35CestPDF", actualListOfContractorRoleDto.getCestDownloadLink());

	}
	
	
	@Test
	public void shouldReturnCESToutcomeFromWithRoleDefaultRate() {
		
		RoleDm roleDm = createRole();
		roleDm.setInsideIr35("");
		RoleDto contractorRoleDto = getContractorRoleDto();
		contractorRoleDto.setInsideIr35("");
		Optional<RoleDm> roleDmList = Optional.ofNullable(roleDm);
		RoleDefaultRate roleDefaultRate = new RoleDefaultRate();
		roleDefaultRate.setRate(new BigDecimal(1));
		roleDefaultRate.setCurrencyId(2L);
		List<RoleDefaultRate> roleDetaultRateList = new ArrayList<>();
		roleDetaultRateList.add(roleDefaultRate);
		
		when(roleRepository.findById(100L)).thenReturn(roleDmList);
		when(roleMapper.toRoleDTO(roleDmList.get())).thenReturn(contractorRoleDto);
		when(roleDefaultRateRepository.findAllByRoleId(100L)).thenReturn(roleDetaultRateList);

		RoleDto actualListOfContractorRoleDto = roleServiceImpl.getCESToutcome(100L);
		assertEquals("2", actualListOfContractorRoleDto.getRoleId());
		assertEquals("Logistics Manager", actualListOfContractorRoleDto.getRoleName());

	}
	

	private RoleDm createRole() {
		RoleDm roleDM = new RoleDm();
		roleDM.setRoleId(2L);
		roleDM.setRoleName("Logistics Manager");
		roleDM.setInsideIr35("true");
		return roleDM;
	}

	private RoleDto getContractorRoleDto() {

		RoleDto contractorRoleDto = new RoleDto();

		contractorRoleDto.setRoleId("2");
		contractorRoleDto.setRoleName("Logistics Manager");
		contractorRoleDto.setRoleDescription("test");
		contractorRoleDto.setInsideIr35("false");

		return contractorRoleDto;

	}

}
