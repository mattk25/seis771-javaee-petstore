package com.SEIS771.proj.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import com.SEIS771.proj.model.Submission;


/** 
 * Utility class to send emails
 * @author Adnan Al-Alawiyat
 *
 */
public class EmailUtility {
	
	private static final String SENDER_USERNAME = "seis771petstore";
	private static final String SENDER_PASSWORD = "petstoreseis771";
	private static final String SENDER_EMAIL = "seis771petstore@gmail.com";
	private static final String MAIL_SERVER_HOSTNAME = "smtp.googlemail.com";
	private static final int MAIL_SERVER_PORT = 465;
	
	public static void sendEmail(String to, String subject, String message) throws Exception {
		
			Email email = new SimpleEmail();
			email.setHostName(MAIL_SERVER_HOSTNAME);
			email.setSmtpPort(MAIL_SERVER_PORT);
			email.setAuthenticator(new DefaultAuthenticator(SENDER_USERNAME, SENDER_PASSWORD));
			email.setSSLOnConnect(true);
			email.setFrom(SENDER_EMAIL);
			email.setSubject(subject);
			email.setMsg(message);
			email.addTo(SENDER_EMAIL);
			email.addTo(to);
			email.send();
		
	
	}
}
