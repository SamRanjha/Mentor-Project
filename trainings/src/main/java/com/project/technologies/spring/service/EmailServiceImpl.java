package com.project.technologies.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

public class EmailServiceImpl {

    public void sendEmail(long mid, long sid) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("ranjhasam80@gmail.com");
        mailSender.setPassword("xkcpbnczbidiotta");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("ranjhasam80@gmail.com");
        msg.setTo("ranjhasam80@gmail.com");
        msg.setSubject("Training Approved");
        msg.setText("Your training for skill " + sid + " with mentor " + mid + " has been approved");
        mailSender.send(msg);
    }

}
