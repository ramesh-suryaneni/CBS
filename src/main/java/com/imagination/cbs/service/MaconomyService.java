/**
 * 
 */
package com.imagination.cbs.service;

import java.io.IOException;

/**
 * @author pappu.rout
 *
 */
public interface MaconomyService {
	
	public <T> T getMaconomyJobNumberAndDepartmentsDetails(String jobNumber, T jobDataDto, String isJobNumberOrDepartmentName, String departname) throws IOException;

}
