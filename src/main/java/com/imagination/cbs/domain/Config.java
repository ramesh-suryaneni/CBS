package com.imagination.cbs.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

/**
 * The persistent class for the config database table.
 * 
 */
@Entity
@Table(name = "config")
@NamedQuery(name = "Config.findAll", query = "SELECT c FROM Config c")
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;

	public Config() {
		// default constructor ignored
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "config_id")
	private long configId;

	@Column(name = "key_description")
	private String keyDescription;

	@Column(name = "key_name")
	private String keyName;

	@Column(name = "key_value")
	private String keyValue;

	@Column(name = "created_date")
	@CreationTimestamp
	private LocalDateTime createdDate;

	public long getConfigId() {
		return this.configId;
	}

	public void setConfigId(long configId) {
		this.configId = configId;
	}

	public String getKeyDescription() {
		return this.keyDescription;
	}

	public void setKeyDescription(String keyDescription) {
		this.keyDescription = keyDescription;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyValue() {
		return this.keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	@Override
	public String toString() {
		return String.format("Config [configId=%s, keyDescription=%s, keyName=%s, keyValue=%s, createdDate=%s]",
				configId, keyDescription, keyName, keyValue, createdDate);
	}

}