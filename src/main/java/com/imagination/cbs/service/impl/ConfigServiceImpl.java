package com.imagination.cbs.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Config;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.ConfigService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService{

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

}
