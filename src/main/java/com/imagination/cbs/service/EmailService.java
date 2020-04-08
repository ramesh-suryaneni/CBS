package com.imagination.cbs.service;

import java.util.Map;

import com.imagination.cbs.dto.MailRequest;

public interface EmailService {

	void sendMail(MailRequest request);
}
