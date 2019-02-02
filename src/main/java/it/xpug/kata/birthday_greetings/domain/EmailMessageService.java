package it.xpug.kata.birthday_greetings.domain;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailMessageService implements MessageService {

    private String smtpHost;
    private int smtpPort;
    private String sender;

    public EmailMessageService(String smtpHost, int smtpPort, String sender) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.sender = sender;
    }

    private Properties sessionProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.smtpHost);
        props.put("mail.smtp.port", "" + this.smtpPort);
        return props;
    }

    private void sendEmail(String subject, String body, String recipient) throws MessagingException {
        // Create a mail session
        Properties props = sessionProperties();
        Session session = Session.getInstance(props, null);

        // Construct the message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(this.sender));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject);
        msg.setText(body);

        // Send the message
        Transport.send(msg);
    }


    private void send(String subject, String body, String recipient) {
        try {
            sendEmail(subject, body, recipient);
        } catch (MessagingException e) {
            throw new SendEmialException("Error during sending email to " + recipient + ":  " + e);
        }
    }


    @Override
    public void send(Messages messagess) {
        send(
                messagess.getSubject(),
                messagess.getBody(),
                messagess.getRecipient()
        );
    }
}
