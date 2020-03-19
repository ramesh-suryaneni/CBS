/**
 * 
 */
package com.imagination.cbs.service;

import java.util.List;

import com.imagination.cbs.dto.JobDataDto;

/**
 * @author pappu.rout
 *
 */
public interface MaconomyService {
	
	public List<JobDataDto> getJobDetails(String jobNumber);

}
