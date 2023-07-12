package controller;

import javax.mail.*;
import javax.mail.internet.*;


import java.util.Properties;


public class SendEmail {



	public static void sendOTP(String email,String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("manikandanpalanidurai@gmail.com", "yvtvxbwlkardjctv");
                    }
                });
      
        
       

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("manikandanpalanidurai@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Shri Balaji Bhavan");
            message.setText("Your Verification OTP is: " +otp );

            Transport.send(message);

            System.out.println("OTP sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

	
}



