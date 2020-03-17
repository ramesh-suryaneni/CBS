package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class DisciplineDto {
	
	private long disciplineId;

	private String disciplineDescription;

	private String disciplineName;

	@Override
	public String toString() {
		return "DisciplineDto [disciplineId=" + disciplineId + ", disciplineDescription=" + disciplineDescription
				+ ", disciplineName=" + disciplineName + "]";
	}
	
	
}
