package com.derivedmed.proj.notification;

import com.derivedmed.proj.model.MailData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

public class MailSender {

    private static MailSender ourInstance = new MailSender();
    private static Logger LOGGER = LogManager.getLogger(MailSender.class);

    public static MailSender getInstance() {
        return ourInstance;
    }

    private MailSender() {
    }

    public void sendMail(MailData mailData) {
        Properties properties = new Properties();

        try (InputStream is = getClass().getResourceAsStream("/mail.properties")) {
            properties.load(is);
            Session mailSession = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(properties.getProperty("mail.smtps.user"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailData.getEmail()));
            message.setSubject("Welcome to " + mailData.getConfName());
            message.setText("Hello, " + mailData.getUserName() + "! U was registered to some reports on " + mailData.getConfName() + " which takes place today at " + mailData.getConfDate() + ", " + mailData.getConfPlace());
            Transport transport = mailSession.getTransport();
            transport.connect("conferencemanager9@gmail.com", "one123581321");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            LOGGER.error("exc", e);
        }
    }
}
