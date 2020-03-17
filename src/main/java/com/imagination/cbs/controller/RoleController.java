/**
 * 
 */
package com.imagination.cbs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.service.RoleService;

/**
 * @author Ramesh.Suryaneni
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/roles")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/{role_id}/cestoutcome")
	public ContractorRoleDto getRoleCESToutcome(Long roleId) {
		
		
		return roleService.getCESToutcome(roleId);
		
	}
	
	

}
