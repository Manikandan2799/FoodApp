package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class VerifyOtp
 */
@WebServlet("/VerifyOtp")
public class VerifyOtp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    JSONObject requestBody = new JSONObject(sb.toString());
	    String email = requestBody.getString("email");
	    String otp = requestBody.getString("otp");
	    if (verifyOtp(request, email, otp)) {
	    	System.out.println("Saran");
	      response.getWriter().print("{\"status\": \"Success\"}");
	      
	    } else {
	    	System.out.println("noo");
	      response.getWriter().print("{\"status\": \"Failure\", \"message\": \"OTP doesn't match. Please try again.\"}");
	    }
	  } 

	  private boolean verifyOtp(HttpServletRequest request, String email, String otp) {
		  boolean b=false;
		  System.out.println("Entered email: " + email);
		    System.out.println("Entered otp: " + otp);
		  Cookie[] cookies = request.getCookies();
	 
	      for (Cookie cookie : cookies) {
	        if (cookie.getName().equals("otp")) {
	        	 String cookieOtp = cookie.getValue(); 
	             System.out.println("Found otp cookie: " + cookie.getValue());

	             if (cookieOtp.equals(otp)) { // compare as integers
	           
	              b=true;
	            }
	        }
		
	      }
		return b;
	    
	  
	  
	
	}
}


