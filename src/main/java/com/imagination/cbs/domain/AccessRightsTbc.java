package com.imagination.cbs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the access_rights_tbc database table.
 * 
 */
@Entity
@Table(name="access_rights_tbc")
public class AccessRightsTbc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_name")
	private String userName;

	@Column(name="fields_tbc")
	private String fieldsTbc;

	public AccessRightsTbc() {
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFieldsTbc() {
		return this.fieldsTbc;
	}

	public void setFieldsTbc(String fieldsTbc) {
		this.fieldsTbc = fieldsTbc;
	}

}