/**
 * 
 */
package com.imagination.cbs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author pappu.rout
 *
 */
@JsonIgnoreProperties(ignoreUnknown =true)
public class ApproverTeamDto {
	
	private ApproverTeamDetailDto data;

	public ApproverTeamDetailDto getData() {
		return data;
	}

	public void setData(ApproverTeamDetailDto data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ApproverTeamDto [data=" + data + "]";
	}
	
	

}
