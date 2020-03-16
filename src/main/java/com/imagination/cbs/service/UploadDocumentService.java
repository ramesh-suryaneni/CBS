package com.imagination.cbs.service;

import org.springframework.core.io.FileSystemResource;

public interface UploadDocumentService {

	String uploadDocUsingTransientDocument(FileSystemResource file);
}
