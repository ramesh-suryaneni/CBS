/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.RoleDefaultRate;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.dto.RoleDto;
import com.imagination.cbs.mapper.RoleMapper;
import com.imagination.cbs.repository.RoleDefaultRateRepository;
import com.imagination.cbs.repository.RoleRepository;
import com.imagination.cbs.service.RoleService;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RoleDefaultRateRepository roleDefaultRateRepository;

	@Autowired
	RoleMapper roleMapper;

	@Value("${cest_outcome.inside_ir35_pdf}")
	private String insideIR35CestPDF;

	@Value("${cest_outcome.outside_ir35_pdf}")
	private String outsideIR35CestPDF;

	@Override
	public RoleDto getCESToutcome(Long roleId) {
		Optional<RoleDm> optionalRole = roleRepository.findById(roleId);
		if (optionalRole.isPresent()) {
			RoleDto dto = roleMapper.toRoleDTO(optionalRole.get());
			String link = (Boolean.valueOf(dto.getInsideIr35())) ? insideIR35CestPDF : outsideIR35CestPDF;
			dto.setCestDownloadLink(link);

			// Fetch role default rate; UI will use if as default day rate if no
			// contractor employee selected.
			List<RoleDefaultRate> rates = roleDefaultRateRepository.findAllByRoleId(roleId);
			if (!rates.isEmpty()) {
				dto.setRoleDefaultRate(String.valueOf(rates.get(0).getRate()));
				dto.setRoleCurrencyId(String.valueOf(rates.get(0).getCurrencyId()));
			}

			return dto;
		}
		return null;
	}

}
