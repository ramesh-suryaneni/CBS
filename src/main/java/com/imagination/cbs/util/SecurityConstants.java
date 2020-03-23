/**
 * 
 */
package com.imagination.cbs.util;

/**
 * @author Ramesh.Suryaneni
 *
 */
public class SecurityConstants {
	
	private SecurityConstants(){
		
	}
	
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
    /** SECURITY ROLES ***/
    public static final String ROLE_ = "ROLE_";
    
    public static final String ROLE_APPROVER = "1";
    public static final String ROLE_BOOKING_CREATOR = "2";
    public static final String ROLE_BOOKING_VIEWER = "3";
    public static final String ROLE_INDEX = "4";
    public static final String ROLE_CONTRACT_MGT = "5";
    public static final String ROLE_PO_MGT = "6";
    public static final String ROLE_ADMIN = "7";
    
}
