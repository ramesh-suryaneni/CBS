package com.imagination.cbs.service;

import java.util.Map;

import com.imagination.cbs.domain.Config;

public interface ConfigService {

	public Config getConfigDetailsByKeyName(String keyName);
	
	public Map<String, String> getAdobeConfigs(String keyName);
}
