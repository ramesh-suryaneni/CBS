/**
 * 
 */
package com.imagination.cbs.service;

/**
 * @author pappu.rout
 *
 */
public interface MaconomyService {
	
	public <T> T getMaconomyJobNumberAndDepartmentsDetails(String jobNumber, T jobDataDto, String isJobNumberOrDepartmentName, String departname);

}
