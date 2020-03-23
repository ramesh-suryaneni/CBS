package com.imagination.cbs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDetailDto {

	private String jobname;
	private String jobnumber;
	private String text3;
	
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

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}
	
		
	
}
