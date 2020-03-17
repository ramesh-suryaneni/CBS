package com.imagination.cbs.service.impl;

import java.util.HashMap;
import static com.imagination.cbs.util.AdobeConstant.*;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.model.AdobeOAuth;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.OAuthService;
import com.imagination.cbs.util.AdobeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OAuthServiceImpl implements OAuthService {

	@Autowired
	private ConfigRepository configRepository;

	public boolean saveOrUpdateAccessToken(AdobeOAuth oAuth) {
		Map<String, Config> result = new HashMap<>();

		try {
			Config c1 = configRepository.findByKeyName(ADOBE_ACCESS_TOKEN).map(c -> {
				c.setKeyValue(oAuth.getAccessToken());
				return configRepository.save(c);
			}).orElseGet(() -> {
				Config con = new Config();
				con.setKeyName(ADOBE_ACCESS_TOKEN);
				con.setKeyValue(oAuth.getAccessToken());
				con.setKeyDescription("Adobe access token");
				return configRepository.save(con);
			});
			result.put(ADOBE_ACCESS_TOKEN, c1);

			Config c2 = configRepository.findByKeyName(ADOBE_REFRESH_TOKEN).map(c -> {
				c.setKeyValue(oAuth.getRefreshToken());
				return configRepository.save(c);
			}).orElseGet(() -> {
				Config con = new Config();
				con.setKeyName(ADOBE_REFRESH_TOKEN);
				con.setKeyValue(oAuth.getRefreshToken());
				con.setKeyDescription("Adobe refresh token");
				return configRepository.save(con);
			});
			result.put(ADOBE_REFRESH_TOKEN, c2);

			Config c3 = configRepository.findByKeyName(ADOBE_TOKEN_TYPE).map(c -> {
				c.setKeyValue(oAuth.getTokenType());
				return configRepository.save(c);
			}).orElseGet(() -> {
				Config con = new Config();
				con.setKeyName(ADOBE_TOKEN_TYPE);
				con.setKeyValue(oAuth.getTokenType());
				con.setKeyDescription("Adobe access token type");
				return configRepository.save(con);
			});
			result.put("ADOBE_TOKEN_TYPE", c3);

			Config c4 = configRepository.findByKeyName(ADOBE_ACCESS_TOKEN_EXP_TIME).map(c -> {
				c.setKeyValue(AdobeUtils.getCurrentDateTime(oAuth.getExpiresIn()));
				return configRepository.save(c);
			}).orElseGet(() -> {
				Config con = new Config();
				con.setKeyName(ADOBE_ACCESS_TOKEN_EXP_TIME);
				con.setKeyValue(AdobeUtils.getCurrentDateTime(oAuth.getExpiresIn()));
				con.setKeyDescription("Adobe access token expired in next one hours");
				return configRepository.save(con);
			});
			result.put(ADOBE_ACCESS_TOKEN_EXP_TIME, c4);
			log.info("result:::{}", new ObjectMapper().writeValueAsString(result));

		} catch (Exception e) {
			log.info("Exception inside saveOrUpdate()::: {}", e);
			return false;
		}
		return true;
	}
}
