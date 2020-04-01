/**
 * 
 */
package com.imagination.cbs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imagination.cbs.dto.RoleDto;
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
	public RoleDto getRoleCESToutcome(@PathVariable("role_id") Long roleId) {
		
		if(roleService.getCESToutcome(roleId) == null)
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "role not found :"+roleId
					);
				
		return roleService.getCESToutcome(roleId);
		
	}
	
	

}
