package com.andon.javaxmaildemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author Andon
 * @date 2019/1/24
 */
@Service
public class SendEmailService {

    private static Logger LOG = LoggerFactory.getLogger(SendEmailService.class);

    @Value("${email.address}")
    private String address;

    public void sendEmail(String title, String content) {
        String[] split = address.split(",");
        Address[] addressesArr = new Address[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                addressesArr[i] = new InternetAddress(split[i]);
            } catch (AddressException e) {
                LOG.error("new InternetAddress failure, error={}", e.getMessage());
            }
        }
        String from = "andon20190123@163.com";
        String authorizationCode = "******"; //授权码
        String host = "smtp.163.com";
//        int port = 25;
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", port);
        // 阿里云服务器 通过JavaMail发送邮箱STMP问题
        // 25端口被禁用 使用SSL协议465端口
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("andon20190123@163.com", authorizationCode);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from,"Andon","UTF-8"));
            message.addRecipients(Message.RecipientType.TO, addressesArr);
            message.setSubject(title);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            LOG.error("sendEmailService transport send message failure,{}", e.getMessage());
        }
    }
}
