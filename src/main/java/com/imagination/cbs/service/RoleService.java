/**
 * 
 */
package com.imagination.cbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.model.Role;
import com.imagination.cbs.repository.RoleRepository;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface RoleService {
	
	public Page<Role> findAll(String sortColumn, String direction, int page, int size);
	
	public List<Role> findByDiscipline(long disciplineId);

}
