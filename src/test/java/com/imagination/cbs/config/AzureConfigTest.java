package com.imagination.cbs.config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import com.imagination.cbs.constant.AzureTemplateConstant;
import com.microsoft.azure.storage.blob.CloudBlobClient;

@RunWith(MockitoJUnitRunner.class)
public class AzureConfigTest {
	
	@InjectMocks
	private AzureConfig azureConfig;
	
	@Mock
	private Environment environment;
	
	
	@Test
	public void shouldReturnCloudBlobClientFromAzure() throws URISyntaxException, InvalidKeyException {
		
		when(environment.getProperty(AzureTemplateConstant.AZURE_STORAGE_CONNECTIONSTRING.getAzureTemplateConst())).thenReturn("DefaultEndpointsProtocol=https;AccountName=imaginationcbs;AccountKey=uE2RZa+UXuI++dhdKlSreySCJvxMVszBhvO1ZLha1W4wFSQi8P8dAx5qBX/wLU7xhPlsUbWtIXdrbDd0tKj5fg==;EndpointSuffix=core.windows.net");
		
		CloudBlobClient cloudBlobClient = azureConfig.cloudBlobClient();
		
		verify(environment).getProperty(AzureTemplateConstant.AZURE_STORAGE_CONNECTIONSTRING.getAzureTemplateConst());
		
		assertEquals("https://imaginationcbs.blob.core.windows.net", cloudBlobClient.getEndpoint().toString());
		
	}
	
}
