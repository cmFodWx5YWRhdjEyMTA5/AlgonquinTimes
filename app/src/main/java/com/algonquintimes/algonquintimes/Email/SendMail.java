package com.algonquintimes.algonquintimes.Email;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Belal on 10/30/2015.
 */

public class SendMail extends javax.mail.Authenticator {

    private String mailhost = "smtp.gmail.com";
    private String user = "appalgonquintimes@gmail.com";
    private String password = "$Ayylmao";
    private String recipient = "appalgonquintimes@gmail.com";
    private Session session;

    private Multipart _multipart = new MimeMultipart();

    static {
        Security.addProvider(new com.algonquintimes.algonquintimes.Email.JSSEProvider());
    }

    SendMail() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.setProperty("mail.smtp.quitwait", "true");
        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(
                    body.getBytes(), "text/html"));
            message.setSender(new InternetAddress(user));
            message.setSubject(subject);
            message.setDataHandler(handler);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html; charset=utf-8");
            _multipart.addBodyPart(messageBodyPart);

            message.setContent(_multipart);
            if (recipient.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipient));
            else
                message.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(recipient));
            Transport.send(message);
        } catch (Exception e) {
            Log.i("Error: ", e.toString());
        }
    }

    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("download image");
        _multipart.addBodyPart(messageBodyPart);
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}
