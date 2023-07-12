package controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    JSONObject requestBody = new JSONObject(sb.toString());
	    String firstname = requestBody.getString("firstname");
	    String lastname = requestBody.getString("lastname");
	    String username = requestBody.getString("username");
	    String email = requestBody.getString("email");
	    String password = requestBody.getString("password");
        System.out.println(firstname);
        System.out.println(lastname);
        System.out.println(username);
        System.out.println(email);
        System.out.println(password);
	    JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("success", true);
	    response.setContentType("application/json");
	    response.getWriter().write(jsonResponse.toString());
	}

}
