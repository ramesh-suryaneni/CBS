/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.RoleDefaultRate;
import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.dto.ContractorRoleDto;
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

	@Override
	public ContractorRoleDto getCESToutcome(Long roleId) {
		Optional<RoleDm> optionalRole = roleRepository.findById(roleId);
		if(optionalRole.isPresent()) {
			ContractorRoleDto dto = roleMapper.toRoleDTO(optionalRole.get());
			String link = (dto.isInsideIr35())? "https://imaginationcbs.blob.core.windows.net/cbs/IR35 Example PDF inside.pdf" : "https://imaginationcbs.blob.core.windows.net/cbs/IR35 Example PDF outside.pdf";
			dto.setCestDownloadLink(link);
			//Fetch role default rate; UI will use if as default day rate if no contractor employee selected.
			/*RoleDefaultRate rate = roleDefaultRateRepository.findByRoleId(roleId);
			if(rate!=null) {
				dto.setRoleDefaultRate(String.valueOf(rate.getRate()));
			}*/
			
			return dto;
		}
		return null;
	}

}
