package com.imagination.cbs.dto;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ContractorRoleDto {
	
	@Setter
	@Getter
	private long roleId;
	
	@Setter
	@Getter
	private String changedBy;
	
	@Setter
	@Getter
	private Timestamp changedDate;
	
	@Setter
	@Getter
	private long disciplineId;
	
	@Setter
	@Getter
	private String roleDescription;
	
	@Setter
	@Getter
	private String roleName;
	
	
	

}
