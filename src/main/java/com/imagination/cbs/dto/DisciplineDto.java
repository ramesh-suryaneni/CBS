package com.imagination.cbs.dto;

import lombok.Data;
/*import lombok.Setter;*/
import lombok.Getter;
import lombok.Setter;

@Data
public class DisciplineDto {
	
	@Setter
	@Getter
	private long disciplineId;

	@Setter
	@Getter
	private String disciplineDescription;

	@Setter
	@Getter
	private String disciplineName;
}
