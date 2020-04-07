package com.imagination.cbs.service.impl;

import static com.imagination.cbs.util.AdobeConstant.ADOBE_ACCESS_TOKEN;
import static com.imagination.cbs.util.AdobeConstant.ADOBE_ACCESS_TOKEN_EXP_TIME;
import static com.imagination.cbs.util.AdobeConstant.ADOBE_REFRESH_TOKEN;
import static com.imagination.cbs.util.AdobeConstant.ADOBE_TOKEN_TYPE;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagination.cbs.domain.Config;
import com.imagination.cbs.dto.AdobeOAuthDto;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.OAuthService;
import com.imagination.cbs.util.AdobeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OAuthServiceImpl implements OAuthService {

	@Autowired
	private ConfigRepository configRepository;

	public boolean saveOrUpdateAccessToken(AdobeOAuthDto adobeOAuthDto) {
		Map<String, Config> result = new HashMap<>();

		try {
			Config c1 = configRepository.findByKeyName(ADOBE_ACCESS_TOKEN).map(c -> {
				c.setKeyValue(adobeOAuthDto.getAccessToken());
				return configRepository.save(c);
			}).orElseGet(() -> {
				Config con = new Config();
				con.setKeyName(ADOBE_ACCESS_TOKEN);
				con.setKeyValue(adobeOAuthDto.getAccessToken());
				con.setKeyDescription("Adobe access token");
				return configRepository.save(con);
			});
			result.put(ADOBE_ACCESS_TOKEN, c1);

			Config c2 = configRepository.findByKeyName(ADOBE_REFRESH_TOKEN).map(c -> {
				c.setKeyValue(adobeOAuthDto.getRefreshToken());
				return configRepository.save(c);
			}).orElseGet(() -> {
				Config con = new Config();
				con.setKeyName(ADOBE_REFRESH_TOKEN);
				con.setKeyValue(adobeOAuthDto.getRefreshToken());
				con.setKeyDescription("Adobe refresh token");
				return configRepository.save(con);
			});
			result.put(ADOBE_REFRESH_TOKEN, c2);

			Config c3 = configRepository.findByKeyName(ADOBE_TOKEN_TYPE).map(c -> {
				c.setKeyValue(adobeOAuthDto.getTokenType());
				return configRepository.save(c);
			}).orElseGet(() -> {
				Config con = new Config();
				con.setKeyName(ADOBE_TOKEN_TYPE);
				con.setKeyValue(adobeOAuthDto.getTokenType());
				con.setKeyDescription("Adobe access token type");
				return configRepository.save(con);
			});
			result.put("ADOBE_TOKEN_TYPE", c3);

			Config c4 = configRepository.findByKeyName(ADOBE_ACCESS_TOKEN_EXP_TIME).map(c -> {
				c.setKeyValue(AdobeUtils.getCurrentDateTime(adobeOAuthDto.getExpiresIn()));
				return configRepository.save(c);
			}).orElseGet(() -> {
				Config con = new Config();
				con.setKeyName(ADOBE_ACCESS_TOKEN_EXP_TIME);
				con.setKeyValue(AdobeUtils.getCurrentDateTime(adobeOAuthDto.getExpiresIn()));
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
