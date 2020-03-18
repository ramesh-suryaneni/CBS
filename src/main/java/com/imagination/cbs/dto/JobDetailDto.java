package com.imagination.cbs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDetailDto {

	private String jobname;
	private String jobnumber;
	
	public String getJobName() {
		return jobname;
	}
	
	public String getJobNumber() {
		return jobnumber;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	
	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}
	
		
	
}
