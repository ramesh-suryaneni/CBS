package com.imagination.cbs.service;

import com.imagination.cbs.domain.Config;

public interface ConfigService {

	Config getConfigDetailsByKeyName(String keyName);
}
