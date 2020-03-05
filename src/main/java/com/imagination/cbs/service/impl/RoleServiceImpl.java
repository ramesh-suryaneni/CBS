/**
 * 
 */
package com.imagination.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.mapper.RoleMapper;
import com.imagination.cbs.model.Role;
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

	//@Autowired
	//private RoleMapper mapper;
	
	@Override
	public Page<Role> findAll(String sortColumn, String direction, int page, int size) {
				
		return null;
	}

	@Override
	public List<Role> findByDiscipline(long disciplineId) {

		System.out.println("RoleServiceImpl findByDiscipline");
		//List<RoleDm> roleDmList =  roleRepository.findByDisciplineId(disciplineId);
		return null;//mapper.toRoleDTOs(roleRepository.findByDisciplineId(disciplineId));
	}

}
