package com.imagination.cbs.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ContractorRoleDto {
	
	private long roleId;
	
	private String changedBy;
	
	private Timestamp changedDate;
	
	private long disciplineId;
	
	private String roleDescription;
	
	private String roleName;
	
	private String cestDownloadLink;
	
	private boolean insideIr35;

	@Override
	public String toString() {
		return "ContractorRoleDto [roleId=" + roleId + ", changedBy=" + changedBy + ", changedDate=" + changedDate
				+ ", disciplineId=" + disciplineId + ", roleDescription=" + roleDescription + ", roleName=" + roleName
				+ "]";
	}
	
	

}
