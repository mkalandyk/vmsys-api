package com.mikiruki.vendingsystemapi.services;

import com.mikiruki.vendingsystemapi.models.Mail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailingService {

    @Autowired
    private Session mailSession;

    @Autowired
    private UserService userService;

    public void sendTestMessage(){
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("vmsservice001@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mkalandyk@gmail.com"));
            message.setSubject("Test Mail");

            String msg = "This is test message";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage(String recipient, String subject, String msg) {
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("vmsservice001@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void notifyAdmin(Mail mail) {
        try {
            this.sendMessage(this.userService.getAdmin().getEmail(), mail.getSubject(), mail.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
