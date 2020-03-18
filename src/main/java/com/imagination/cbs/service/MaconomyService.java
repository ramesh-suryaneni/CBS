/**
 * 
 */
package com.imagination.cbs.service;

import java.util.List;

/**
 * @author pappu.rout
 *
 */
public interface MaconomyService {
	
	public List<com.imagination.cbs.dto.JobDataDto> getJobDetails(String jobNumber);

}
