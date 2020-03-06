/**
 * 
 */
package com.imagination.cbs.service;

import java.util.List;

import com.imagination.cbs.dto.ContractorRoleDto;
import com.imagination.cbs.dto.DisciplineDto;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingService {

	public List<DisciplineDto> getAllDisciplines();

	public List<ContractorRoleDto> getAllContractorRoles(Long disciplineId);

}
