package com.realnet.config;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleMessage(String from, String to, String subject, String text) throws MailException {
		SimpleMailMessage message = new SimpleMailMessage();
		log.debug("email to sent..");
		//message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
		log.debug("email sent successfully");
	}
	
	 public void sendEmailWithAttachment(String to, String subject, String text) throws MessagingException, IOException {

	        MimeMessage msg = mailSender.createMimeMessage();

	        // true = multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
	        log.debug("email to sent..");
	        helper.setTo(to);

	        helper.setSubject(subject);

	        // default = text/plain
	        //helper.setText("Check attachment for image!");

	        // true = text/html
	        //helper.setText("<h1>Check attachment for image!</h1>", true);

	        helper.setText(text, true);

	        // hard coded a file path
	        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));
	        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
	        mailSender.send(msg);
	        log.debug("email sent successfully");
	    }
	 
	 public void constructEmail( String em, String subject, String url) {
		    SimpleMailMessage email = new SimpleMailMessage();
		    email.setSubject(subject);
		    email.setText(url);
		    email.setTo(em);
		    mailSender.send(email);
			
		}
	//FOR ADD USER VIA ADMIN
		 public void adduserviaadmin( String em, String subject, String url) {
			    SimpleMailMessage email = new SimpleMailMessage();
			    email.setSubject(subject);
			    email.setText(url);
			    email.setTo(em);
			    mailSender.send(email);
				
			}

}
