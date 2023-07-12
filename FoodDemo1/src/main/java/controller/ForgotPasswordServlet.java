package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.DbConnection;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final Random random = new Random();

	private final String EMAIL_HOST = "smtp.gmail.com";
	private final int EMAIL_PORT = 587;
	private final String EMAIL_USERNAME = "manikandanpalanidurai@gmail.com";
	private final String EMAIL_PASSWORD = "yvtvxbwlkardjctv";
	private final String EMAIL_SUBJECT = "Password Reset OTP";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		System.out.println(email);
		  try (Connection connection = DbConnection.init();) {
		        String sql = "SELECT * FROM members WHERE email = ?";
		        PreparedStatement statement = connection.prepareStatement(sql);
		        statement.setString(1, email);

		        ResultSet resultSet = statement.executeQuery();
		        if (resultSet.next()) {
		            int otp = generateOtp();
		            sendEmail(email, otp, response);
		            response.setContentType("application/json");
		            response.getWriter().print("{\"status\": \"Success\", \"otp\": \"" + otp + "\"}");
		        } else {
		        	response.setContentType("application/json");
		            response.getWriter().print("{\"failure\": \"Invalid email id\"}");
		        }
		    } catch (SQLException | MessagingException e) {
		        response.getWriter().print("{\"status\": \"Failure\"}");
		    }
		}

	private int generateOtp() {
		return random.nextInt(900000) + 100000;
	}

	private void sendEmail(String email, int otp,HttpServletResponse response) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", EMAIL_HOST);
		props.put("mail.smtp.port", EMAIL_PORT);

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(EMAIL_USERNAME));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		message.setSubject(EMAIL_SUBJECT);
		message.setText("Your OTP for password reset is: " + otp);
        
		Cookie otpCookie = new Cookie("otp", Integer.toString(otp));
		otpCookie.setMaxAge(300); // Set cookie expiration time to 5 minutes
		response.addCookie(otpCookie);
		
		Transport.send(message);
	}
}
