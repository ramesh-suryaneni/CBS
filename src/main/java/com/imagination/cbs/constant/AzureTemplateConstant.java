/**
 * 
 */
package com.imagination.cbs.constant;

/**
 * @author pappu.rout
 *
 */
public enum AzureTemplateConstant {
	
	AZURE_STORAGE_CONNECTIONSTRING("azure.storage.connectionString"),
	AZURE_STORAGE_CONTAINER_NAME("azure.storage.container.name");
	
	private String azureTemplateConstant;

	AzureTemplateConstant(String azureTemplateConstant) {
		this.azureTemplateConstant = azureTemplateConstant;
	}

	public String getAzureTemplateConstant() {
		return azureTemplateConstant;
	}
	
	

}
