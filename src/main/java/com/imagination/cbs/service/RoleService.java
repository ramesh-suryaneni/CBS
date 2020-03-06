/**
 * 
 */
package com.imagination.cbs.service;

import java.util.List;

import com.imagination.cbs.dto.ContractorRoleDto;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface RoleService {
	
	List<ContractorRoleDto> findAllContractorRoles(Long disciplineId);
	
	//public Page<Role> findAll(String sortColumn, String direction, int page, int size);
	

}
