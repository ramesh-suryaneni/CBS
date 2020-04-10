package com.imagination.cbs.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imagination.cbs.exception.CBSApplicationException;
import com.imagination.cbs.exception.ResourceNotFoundException;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

@Component("azureStorageUtility")
public class AzureStorageUtility {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CloudBlobClient cloudBlobClient;

	@Autowired
	private CloudBlobContainer cloudBlobContainer;

	public List<URI> getAllTemplatesList(String containerName) {

		logger.info("containerName:::{}", containerName);
		List<URI> uris = null;

		try {
			uris = new ArrayList<>();

			CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);

			for (ListBlobItem blobItem : container.listBlobs()) {
				uris.add(blobItem.getUri());
			}

		} catch (Exception exception) {
			throw new CBSApplicationException(exception.getLocalizedMessage());
		}

		return uris;
	}

	public String downloadTemplateAsText(String templateName) throws URISyntaxException, StorageException {

		logger.info("templateName:::{}", templateName);

		CloudBlockBlob blob = null;
		String templateText = null;

		try {

			blob = cloudBlobContainer.getBlockBlobReference(templateName);

			if (blob.exists()) {
				blob.downloadToFile(blob.getName());
				templateText = blob.downloadText();
				logger.info("blobName::{} blobText::{}", blob.getName(), templateText);
			}

		} catch (Exception exception) {

			throw new CBSApplicationException(exception.getLocalizedMessage());

		}

		return templateText;
	}
	
	public URI uploadFile(InputStream inputStream, String fileName) {

		URI uri = null;
		CloudBlockBlob cloudBlockBlob = null;

		try {

			cloudBlockBlob = cloudBlobContainer.getBlockBlobReference(fileName);
			//TODO : check if file already exits
			cloudBlockBlob.upload(inputStream, -1);
			uri = cloudBlockBlob.getUri();

			logger.info("file uri::{}", uri);

		} catch (URISyntaxException e) {
			throw new CBSApplicationException(e.getMessage());

		} catch (StorageException e) {
			throw new CBSApplicationException(e.getMessage());

		} catch (IOException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}

		return uri;
	}

}
