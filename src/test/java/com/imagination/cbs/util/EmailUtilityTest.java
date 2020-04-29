package com.imagination.cbs.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import com.imagination.cbs.dto.MailRequest;
import com.imagination.cbs.dto.MailResponse;

@RunWith(MockitoJUnitRunner.class)
public class EmailUtilityTest {

	@InjectMocks
	private EmailUtility emailUtility;
	
	@Mock
	private JavaMailSender sender;
	
	private MailRequest mailRequest;
	
	@Before
	public void init() {
		mailRequest = new MailRequest();
		mailRequest.setMailTo(new String[] {"receiver@imagination.com"});
		mailRequest.setSubject("Test mail");

		final MimeMessage message = mock(MimeMessage.class);
		when(sender.createMimeMessage()).thenReturn(message);
	}
	
	@Test
	public void sendEmail_shouldCreateMineMessage_beforeSendingMail() throws MessagingException {
		
		emailUtility.sendEmail(mailRequest, "Test mail body for send Email");
		verify(sender).createMimeMessage();
	}
	
	@Test
	public void sendEmail_shouldSendResponse_whenMailSent() throws MessagingException {
	
		MailResponse actual = emailUtility.sendEmail(mailRequest, "Test mail body for send Email");
		assertTrue(actual.isStatus());
	}

}
