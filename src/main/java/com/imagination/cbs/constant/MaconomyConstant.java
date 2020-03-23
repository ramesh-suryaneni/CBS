/**
 * 
 */
package com.imagination.cbs.constant;

/**
 * @author pappu.rout
 *
 */
public enum MaconomyConstant {
	
	MACANOMY("MACONOMY"),
	MACANOMY_URL("MACONOMY_BASE_URL"),
	MACONOMY_USER_NAME("MACONOMY_USERNAME"),
	MACONOMY_PASSWORD("MACONOMY_PASSWORD"),
	MACONOMY_JOB_NUMBER("jobs/data;jobnumber="),
	MACONOMY_HEADER_KEY("Maconomy-Authentication"),
	MACONOMY_HEADER_VALUE("X-Force-Maconomy-Credentials"),
	MEDIA_TYPE("application/json"),
	MACANOMY_REVENUS_DEPARTMENT("/find_theoption/filter?fields=name,remark3&restriction=optionlistnumber "
			+ "= \"JobRevenueDepartment\"&limit=1000");
			
	
	private String macanomy;

	private MaconomyConstant(String macanomy) {
		this.macanomy = macanomy;
	}

	public String getMacanomy() {
		return macanomy;
	}
	
	

}
