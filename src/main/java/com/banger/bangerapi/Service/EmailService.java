package com.banger.bangerapi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

@Autowired
    private org.springframework.mail.javamail.JavaMailSender javaMailSender;

    public void sendEmail(String License) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("bangersandco1990@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("This License number has requested for a vehicle booking "+License);

        javaMailSender.send(msg);

    }
}
