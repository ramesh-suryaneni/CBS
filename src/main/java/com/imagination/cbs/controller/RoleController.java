/**
 * 
 */
package com.imagination.cbs.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.domain.RoleDm;
import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.model.Direction;
import com.imagination.cbs.model.OrderBy;
import com.imagination.cbs.model.Role;
import com.imagination.cbs.service.RoleService;
import com.imagination.cbs.util.ErrorMessageConstant;

/**
 * @author Ramesh.Suryaneni
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/roles")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	
	@GetMapping(params = { "orderBy", "direction", "page", "size" })
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
	}
	
	
	@GetMapping(value = "roleByDiscipline/{disciplineId}")
	public ResponseEntity<List<Role>> findRoleByDiscipline(@PathVariable(value = "disciplineId") long disciplineId){
		System.out.println("RoleController called");
		List<Role> rolesByDisciplineId = roleService.findByDiscipline(disciplineId);
		return ResponseEntity.ok().body(rolesByDisciplineId);
	}

}
