package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the access_rights_tbc database table.
 * 
 */
@Entity
@Table(name="access_rights_tbc")
@NamedQuery(name="AccessRightsTbc.findAll", query="SELECT a FROM AccessRightsTbc a")
public class AccessRightsTbc implements Serializable {

	private static final long serialVersionUID = 535845536742218795L;

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