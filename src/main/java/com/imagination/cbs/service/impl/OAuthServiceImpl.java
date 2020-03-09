package com.imagination.cbs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagination.cbs.domain.Config;
import com.imagination.cbs.model.AdobeOAuth;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.OAuthService;
import com.imagination.cbs.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OAuthServiceImpl implements OAuthService {

	@Autowired
	private ConfigRepository configRepository;

	@Override
	public boolean saveOrUpdateAccessToken(Map<String, Config> keys, AdobeOAuth oAuth) {
		log.info("keys check:::{keys,oAuth}", keys,oAuth);
		
		List<?> result =new ArrayList<>();
		
		if (isNullOrEmptyMap(keys)) {
			
			log.info("access token inserted:::{}");
			
			List<Config> list = new ArrayList<>();
			Config c1 = new Config();
			c1.setKeyName("ADOBE_ACCESS_TOKEN");
			c1.setKeyValue(oAuth.getAccess_token());
			c1.setKeyDescription("Adobe Access token");
			list.add(c1);

			Config c2 = new Config();
			c2.setKeyName("ADOBE_REFRESH_TOKEN");
			c2.setKeyValue(oAuth.getRefresh_token());
			c2.setKeyDescription("Adobe refresh token");
			list.add(c2);

			Config c3 = new Config();
			c3.setKeyName("ADOBE_TOKEN_TYPE");
			c3.setKeyValue(oAuth.getToken_type());
			c3.setKeyDescription("Adobe token type");
			list.add(c3);

			Config c4 = new Config();
			c4.setKeyName("ADOBE_ACCESS_TOKEN_EXP_TIME");
			c4.setKeyValue(Utils.getCurrentDateTime());
			c4.setKeyDescription("Adobe Access token Exp time");
			list.add(c4);

			result=configRepository.saveAll(list);

		} else {

			List<Config> list = new ArrayList<>();
			log.info("access token updated:::{}");
			Config c1 = new Config();
			c1.setConfigId(keys.get("ADOBE_ACCESS_TOKEN").getConfigId());
			c1.setKeyName("ADOBE_ACCESS_TOKEN");
			c1.setKeyValue(oAuth.getAccess_token());
			c1.setKeyDescription("Adobe Access token");
			list.add(c1);

			Config c2 = new Config();
			c2.setConfigId(keys.get("ADOBE_REFRESH_TOKEN").getConfigId());
			c2.setKeyName("ADOBE_REFRESH_TOKEN");
			c2.setKeyValue(oAuth.getRefresh_token());
			c2.setKeyDescription("Adobe refresh token");
			list.add(c2);

			Config c3 = new Config();
			c3.setConfigId(keys.get("ADOBE_TOKEN_TYPE").getConfigId());
			c3.setKeyName("ADOBE_TOKEN_TYPE");
			c3.setKeyValue(oAuth.getToken_type());
			c3.setKeyDescription("Adobe token type");
			list.add(c3);

			Config c4 = new Config();
			c4.setConfigId(keys.get("ADOBE_ACCESS_TOKEN_EXP_TIME").getConfigId());
			c4.setKeyName("ADOBE_ACCESS_TOKEN_EXP_TIME");
			c4.setKeyValue(Utils.getCurrentDateTime());
			c4.setKeyDescription("Adobe Access token Exp time");
			list.add(c4);
			
			result=configRepository.saveAll(list);

		}
		return (result == null || result.size() == 0) ? false : true;
	}

	public static boolean isNullOrEmptyMap(Map<?, ?> map) {

		return (map == null || map.isEmpty());
	}

}
