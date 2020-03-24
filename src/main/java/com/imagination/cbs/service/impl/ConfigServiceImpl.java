package com.imagination.cbs.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Config;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.ConfigService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ConfigRepository configRepository;
	
	@Override
	@Cacheable("google_keys")
	public Config getConfigDetailsByKeyName(String keyName) {
		
		Optional<Config> optionalConfig = configRepository.findByKeyName(keyName);
		
		if(optionalConfig.isPresent())
			return optionalConfig.get();
		else 
			throw new ResourceNotFoundException("Configuration not found with Key :" + keyName);
		
	}
	
	
	@Cacheable("adobe_configs")
	public Map<String, String> getAdobeConfigs(String keyName) {

		logger.info("keyName:::{}", keyName);

		Map<String, String> map = null;
		List<Config> keysList = null;

		keysList = configRepository.findBykeyNameStartingWith(keyName);

		if (keysList == null || keysList.isEmpty())
			return null;

		map = keysList.stream().filter(key -> key.getKeyValue() != null || !key.getKeyValue().isEmpty())
				.collect(Collectors.toMap(Config::getKeyName, Config::getKeyValue));

		if (map == null || map.isEmpty())
			return null;

		return map;
	}

}
