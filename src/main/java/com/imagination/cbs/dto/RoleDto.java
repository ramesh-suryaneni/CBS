package com.imagination.cbs.dto;

import lombok.Data;

@Data
public class RoleDto {

	private String roleId;

	private String changedBy;

	private String changedDate;

	private String disciplineId;

	private String roleDescription;

	private String roleName;

	private String cestDownloadLink;

	private String insideIr35;

	private String roleDefaultRate;

	private String roleCurrencyId;

	private String status;

	@Override
	public String toString() {
		return "ContractorRoleDto [roleId=" + roleId + ", changedBy=" + changedBy + ", changedDate=" + changedDate
				+ ", disciplineId=" + disciplineId + ", roleDescription=" + roleDescription + ", roleName=" + roleName
				+ ", cestDownloadLink=" + cestDownloadLink + ", insideIr35=" + insideIr35 + ", roleDefaultRate="
				+ roleDefaultRate + ", roleCurrencyId=" + roleCurrencyId + "]";
	}

}
