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
	
	private String azureTemplateConst;

	AzureTemplateConstant(String azureTemplateConst) {
		this.azureTemplateConst = azureTemplateConst;
	}

	public String getAzureTemplateConst() {
		return azureTemplateConst;
	}
	
	

}
