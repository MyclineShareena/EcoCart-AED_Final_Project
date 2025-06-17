/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

/**
 *
 * @author Shari
 */

public class EmailUtil {
    public static void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        String senderEmail = "myclineshari@gmail.com"; // replace with your email
        String senderPassword = "rspc uidw nlgr wael";   // use app password if using Gmail

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(recipientEmail)
        );
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}

