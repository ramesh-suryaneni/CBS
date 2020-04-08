package com.imagination.cbs.service;

import com.imagination.cbs.dto.MailRequest;

public interface EmailService {

	void sendMail(MailRequest request);
}
