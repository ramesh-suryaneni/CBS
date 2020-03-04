/**
 * 
 */
package com.imagination.cbs.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.imagination.cbs.model.Role;
import com.imagination.cbs.service.RoleService;

/**
 * @author Ramesh.Suryaneni
 *
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Override
	public Page<Role> findAll(String sortColumn, String direction, int page, int size) {
				
		return null;
	}

}
