package com.app.hotelbooking.service;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

            //String token = generateToken();
            //String newBody = body.concat(token);

            mimeMessageHelper.setFrom("georgitest19@gmail.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);

            javaMailSender.send(mimeMessage);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
