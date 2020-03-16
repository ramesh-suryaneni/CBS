package com.imagination.cbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.DisciplineDto;
import com.imagination.cbs.service.DisciplineService;
import com.imagination.cbs.service.RoleService;

/**
 * @author Pappu Rout
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/disciplines")
public class DisciplineController {

	@Autowired
	private DisciplineService disciplineService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public List<DisciplineDto> getAllDisciplines() {
		return disciplineService.getAllDisciplines();

	}
	
	@GetMapping("/{disciplineId}/roles")
	public List<ContractorRoleDto> findAllContractorRoles(@PathVariable Long disciplineId) {
		
		return roleService.findAllContractorRoles(disciplineId);

	}

}
