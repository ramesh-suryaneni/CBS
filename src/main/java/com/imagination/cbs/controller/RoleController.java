/**
 * 
 */
package com.imagination.cbs.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "/contractor_roles")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/{disciplineId}")
	public List<ContractorRoleDto> findAllContractorRoles(@PathVariable Long disciplineId) {
		
		return roleService.findAllContractorRoles(disciplineId);

	}
	
	
	/*@GetMapping(params = { "orderBy", "direction", "page", "size" })
	public @ResponseBody Page<Role> findAll(@RequestParam("orderBy") String orderBy,
			@RequestParam("direction") String direction, @RequestParam("page") int page,
			@RequestParam("size") int size){
		
    	if (!(direction.equals(Direction.ASCENDING.getDirectionCode())
				|| direction.equals(Direction.DESCENDING.getDirectionCode()))) {
			throw new CBSApplicationException(ErrorMessageConstant.INVALID_SORT_DIRECTION);
		}
		if (!(orderBy.equals(OrderBy.PRIORITY.getOrderByCode()))) {
			throw new CBSApplicationException(ErrorMessageConstant.INVALID_ORDER_BY);
			
		}
		return roleService.findAll(orderBy, direction, page, size);
	}*/

}
