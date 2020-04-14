/**
 * 
 */
package com.imagination.cbs.util;

/**
 * @author Ramesh.Suryaneni
 *
 */
public class SecurityConstants {

	private SecurityConstants() {

	}

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	/** SECURITY ROLES ***/
	public static final String ROLE_ = "ROLE_";

	public static final String ROLE_APPROVER = "Approver";
	public static final String ROLE_BOOKING_CREATOR = "Booking Creator";
	public static final String ROLE_BOOKING_VIEWER = "Booking Viewer";
	public static final String ROLE_INDEX = "Index Access";
	public static final String ROLE_CONTRACT_MGT = "Contract Management";
	public static final String ROLE_PO_MGT = "PO Management";
	public static final String ROLE_ADMIN = "Admin";

	public static final Long ROLE_APPROVER_ID = 1L;
	public static final Long ROLE_BOOKING_CREATOR_ID = 2L;
	public static final Long ROLE_BOOKING_VIEWER_ID = 3L;
	public static final Long ROLE_INDEX_ID = 4L;
	public static final Long ROLE_CONTRACT_MGT_ID = 5L;
	public static final Long ROLE_PO_MGT_ID = 6L;
	public static final Long ROLE_ADMIN_ID = 7L;

}
