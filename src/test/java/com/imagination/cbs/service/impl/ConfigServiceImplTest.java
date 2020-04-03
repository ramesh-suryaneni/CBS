package com.imagination.cbs.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.domain.Config;
import com.imagination.cbs.repository.ConfigRepository;

@RunWith(MockitoJUnitRunner.class)
public class ConfigServiceImplTest {

	@InjectMocks
	private ConfigServiceImpl configServiceImpl;

	@Mock
	private ConfigRepository configRepository;

	@Test
	public void shouldReturnGoogleConfigDetailsByKeyName() {
		String keyName = "GOOGLE";

		Optional<Config> googleKeyValue = Optional.ofNullable(getGoogleKeyValue());

		when(configRepository.findByKeyName(keyName)).thenReturn(googleKeyValue);

		Config actualGoogleKeyValue = configServiceImpl.getConfigDetailsByKeyName(keyName);

		assertEquals("GOOGLE_ID", actualGoogleKeyValue.getKeyName());
		assertEquals("73478530580-60km8n2mheo2e0e5qmg57617qae6fqij.apps.googleusercontent.com",
				actualGoogleKeyValue.getKeyValue());

	}

	private Config getGoogleKeyValue() {
		Config obj = new Config();
		obj.setConfigId(2L);
		obj.setKeyName("GOOGLE_ID");
		obj.setKeyValue("73478530580-60km8n2mheo2e0e5qmg57617qae6fqij.apps.googleusercontent.com");
		return obj;
	}

	@Test
	public void shouldReturnListOfAdobeConfigDetailsByKeyName() {
		String keyName = "ADOBE";

		List<Config> listOfAdobeConfigKeyValue = getAdobeConfigKeyValue();

		when(configRepository.findBykeyNameStartingWith(keyName)).thenReturn(listOfAdobeConfigKeyValue);

		Map<String, String> actualAdobeKeyValue = configServiceImpl.getAdobeConfigs(keyName);

		assertEquals("TEST_TOKEN", actualAdobeKeyValue.get("ADOBE_TOKEN"));
		assertEquals("TEST_CLIENT_ID", actualAdobeKeyValue.get("ADOBE_CLIENT_ID"));

	}

	private List<Config> getAdobeConfigKeyValue() {

		List<Config> list = new ArrayList<Config>();

		Config obj = new Config();
		obj.setKeyName("ADOBE_TOKEN");
		obj.setKeyValue("TEST_TOKEN");

		Config obj1 = new Config();
		obj1.setKeyName("ADOBE_CLIENT_ID");
		obj1.setKeyValue("TEST_CLIENT_ID");

		list.add(obj);
		list.add(obj1);

		return list;
	}

}
