/**
 * 
 */
package com.imagination.cbs.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Ramesh.Suryaneni
 *
 */
public class CBSUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<? extends GrantedAuthority> authorities;

	private String email;

	private Long empId;

	private String maconomyEmpNumber;

	private String displayName;

	public CBSUser(String displayName) {
		this.displayName = displayName;

	}

	/**
	 * @param authorities
	 * @param email
	 * @param empId
	 * @param maconomyEmpNumber
	 * @param displayName
	 * @param password
	 * @param username
	 * @param enabled
	 * @param accountNonExpired
	 * @param accountNonLocked
	 * @param credentialsNonExpired
	 */
	public CBSUser(String email, Long empId, String maconomyEmpNumber, String displayName, String password,
			String username, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.email = email;
		this.empId = empId;
		this.maconomyEmpNumber = maconomyEmpNumber;
		this.displayName = displayName;
		this.password = password;
		this.username = username;
		this.authorities = authorities;

		this.enabled = true;
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;

	}

	private String password;

	private String username;

	private boolean enabled;

	private boolean accountNonExpired;

	private boolean accountNonLocked;

	private boolean credentialsNonExpired;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the empId
	 */
	public Long getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	/**
	 * @return the maconomyEmpNumber
	 */
	public String getMaconomyEmpNumber() {
		return maconomyEmpNumber;
	}

	/**
	 * @param maconomyEmpNumber
	 *            the maconomyEmpNumber to set
	 */
	public void setMaconomyEmpNumber(String maconomyEmpNumber) {
		this.maconomyEmpNumber = maconomyEmpNumber;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
