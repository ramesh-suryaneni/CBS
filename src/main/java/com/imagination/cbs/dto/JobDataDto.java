package com.imagination.cbs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDataDto {

	private JobDetailDto data;

	public JobDetailDto getData() {
		return data;
	}

	public void setData(JobDetailDto data) {
		this.data = data;
	}
}
