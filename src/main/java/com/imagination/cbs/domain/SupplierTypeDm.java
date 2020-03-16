package com.imagination.cbs.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the supplier_type_dm database table.
 * 
 */
@Entity
@Table(name = "supplier_type_dm")
@NamedQuery(name = "SupplierTypeDm.findAll", query = "SELECT s FROM SupplierTypeDm s")
public class SupplierTypeDm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String description;

	private String name;

	public SupplierTypeDm() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}