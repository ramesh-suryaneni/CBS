/*package com.imagination.cbs.service.impl;

import static com.imagination.cbs.util.AdobeConstant.ADOBE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.imagination.cbs.domain.Config;
import com.imagination.cbs.repository.ConfigRepository;
import com.imagination.cbs.service.OAuthService;

@RunWith(MockitoJUnitRunner.class)
public class AdobeOAuthTokensServiceImplTest {

	@Mock
	private ConfigRepository configRepository;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private Environment env;

	@Mock
	private OAuthService oAuthService;

	@InjectMocks
	private AdobeOAuthTokensServiceImpl adobeOAuthTokensServiceImpl;

	@Test
	public void shouldReturnOauthAccessTokenWhenConfigIsNotNull() {
		
		when(configRepository.findBykeyNameStartingWith(ADOBE)).thenReturn(getAdobeKeyDetails(ADOBE));
		
		String actual=adobeOAuthTokensServiceImpl.getOauthAccessToken();
		
		verify(configRepository).findBykeyNameStartingWith(ADOBE);
		
		assertEquals("Bearer XYZ", actual);

	}

	@Test
	public void shouldReturnOauthAccessTokenWhenConfigIsNull() {
		
		when(configRepository.findBykeyNameStartingWith("XYZ")).thenReturn(getAdobeKeyDetails("XYZ"));
		
		String actual=adobeOAuthTokensServiceImpl.getOauthAccessToken();
		verify(configRepository).findBykeyNameStartingWith("XYZ");
		assertEquals("Bearer XYZ", actual);
		
	}

	public List<Config> getAdobeKeyDetails(String keyName) {
		List<Config> keysList = null;
		if (keyName == "ADOBE")
			keysList = new ArrayList<>();// DB
		Config c1 = new Config();
		c1.setKeyName("ADOBE_ACCESS_TOKEN");
		c1.setKeyValue("XYZ");
		keysList.add(c1);

		Config c2 = new Config();
		c2.setKeyName("ADOBE_ACCESS_TOKEN_EXP_TIME");
		c2.setKeyValue(Timestamp.valueOf(LocalDateTime.now().plusHours(1)).toString());
		keysList.add(c2);

		Config c3 = new Config();
		c3.setKeyName("ADOBE_REFRESH_TOKEN");
		c3.setKeyValue("XYZ_REFRESH*");
		keysList.add(c3);

		return keysList;

	}

}
*/