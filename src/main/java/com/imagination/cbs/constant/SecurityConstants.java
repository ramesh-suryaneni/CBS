/**
 * 
 */
package com.imagination.cbs.constant;

/**
 * @author pappu.rout
 *
 */
public enum SecurityConstants {
	
	 TOKEN_PREFIX("Bearer "),
	 HEADER_STRING("Authorization"),

	/** SECURITY ROLES ***/
	 ROLE_("ROLE_"),

	 ROLE_APPROVER("Approver"),
	 ROLE_BOOKING_CREATOR("Booking Creator"),
	 ROLE_BOOKING_VIEWER("Booking Viewer"),
	 ROLE_INDEX("Index Access"),
	 ROLE_CONTRACT_MGT("Contract Management"),
	 ROLE_PO_MGT("PO Management"),
	 ROLE_ADMIN("Admin"),

	 ROLE_APPROVER_ID(1L),
	 ROLE_BOOKING_CREATOR_ID(2L),
	 ROLE_BOOKING_VIEWER_ID(3L),
	 ROLE_INDEX_ID(4L),
	 ROLE_CONTRACT_MGT_ID(5L),
	 ROLE_PO_MGT_ID(6L),
	 ROLE_ADMIN_ID(7L);
	
	private String securityConstants;

	private SecurityConstants(String securityConstants) {
		this.securityConstants = securityConstants;
	}

	public String getSecurityConstants() {
		return securityConstants;
	}
	
	private Long roleDetails;

	public Long getRoleDetails() {
		return roleDetails;
	}
	
	private SecurityConstants(Long roleDetails) {
		this.roleDetails = roleDetails;
	}
	
	
	
	
	

}
