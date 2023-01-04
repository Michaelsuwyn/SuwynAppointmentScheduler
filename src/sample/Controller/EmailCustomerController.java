package sample.Controller;

import com.sun.javafx.charts.Legend;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailCustomerController {
    public TextField toField;
    public TextField subjectField;
    public TextArea messageField;

    public void sendEmail(ActionEvent actionEvent) throws MessagingException {
        AllCustomerController.emailStage.close();
        sendMail(toField.getText().toString(), subjectField.getText().toString(), messageField.getText().toString());
    }

    public static void sendMail(String recipient, String subject, String messageText) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "apptschedulerproject@gmail.com";
        String password = "nxzldyxeizddyule";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, recipient, subject, messageText);
        Transport.send(message);
        System.out.println("message sent successfully");
    }

    public static Message prepareMessage(Session session, String myAccountEmail, String recipient, String subject, String messageText) {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(messageText);
            return message;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}

