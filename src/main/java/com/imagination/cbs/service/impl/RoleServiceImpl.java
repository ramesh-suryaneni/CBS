/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.mapper.RoleMapper;
import com.imagination.cbs.repository.RoleRepository;
import com.imagination.cbs.service.RoleService;



/**
 * @author Ramesh.Suryaneni
 *
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<ContractorRoleDto> findAllContractorRoles(Long disciplineId) {
		
		return roleMapper.toListContractorRoleDto(roleRepository.findByDisciplineId(disciplineId));
	}


	

}
