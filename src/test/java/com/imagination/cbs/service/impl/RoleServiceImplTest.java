/**
 * 
 */
package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

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

		Long roleId = 100L;

		Optional<RoleDm> roleDm = Optional.ofNullable(getRole());
		when(roleRepository.findById(roleId)).thenReturn(roleDm);
		when(roleMapper.toRoleDTO(roleDm.get())).thenReturn(getContractorRoleDto());

		RoleDto actualListOfContractorRoleDto = roleServiceImpl.getCESToutcome(roleId);
		assertEquals("2", actualListOfContractorRoleDto.getRoleId());
		assertEquals("Logistics Manager", actualListOfContractorRoleDto.getRoleName());

	}

	private RoleDm getRole() {
		RoleDm obj = new RoleDm();
		obj.setRoleId(2L);
		obj.setRoleName("Logistics Manager");
		return obj;
	}

	private RoleDto getContractorRoleDto() {

		RoleDto contractorRoleDto = new RoleDto();

		contractorRoleDto.setRoleId("2");
		contractorRoleDto.setRoleName("Logistics Manager");
		contractorRoleDto.setRoleDescription("test");

		return contractorRoleDto;

	}

}
