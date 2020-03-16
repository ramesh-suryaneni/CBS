package com.imagination.cbs.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ContractorRoleDtoTest {
	
	@Test
	public void verifyToString(){
		
		ContractorRoleDto contractorRole = new ContractorRoleDto();
		contractorRole.setDisciplineId(7000L);
		
		StringBuilder expexted = new StringBuilder();
		
		expexted.append("ContractorRoleDto [roleId=");
		expexted.append("0");
		expexted.append(", changedBy=null");
		expexted.append(", changedDate=");
		expexted.append("null");
		expexted.append(", disciplineId=");
		expexted.append(7000L);
		expexted.append(", roleDescription=");
		expexted.append("null");
		expexted.append(", roleName=");
		expexted.append("null]");
		
		assertEquals(expexted.toString(), contractorRole.toString());
	}

}
