/**
 * 
 */
package com.imagination.cbs.service;

import org.springframework.data.domain.Page;

import com.imagination.cbs.model.Role;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface RoleService {
	
	public Page<Role> findAll(String sortColumn, String direction, int page, int size);

}
