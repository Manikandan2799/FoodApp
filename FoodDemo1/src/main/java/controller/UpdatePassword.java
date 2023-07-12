package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import view.DbConnection;


@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet {
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
	    String newPassword = requestBody.getString("newPassword");
	    System.out.println(email);
	    System.out.println(newPassword);

	    try {
	      Connection conn = DbConnection.init(); // establish MySQL connection
	      String query = "UPDATE members SET password = ? WHERE email = ?";
	      PreparedStatement preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString(1, newPassword);
	      preparedStmt.setString(2, email);
	      int rowsUpdated = preparedStmt.executeUpdate();
	      if (rowsUpdated > 0) {
	        response.getWriter().print("{\"status\": \"Success\"}");
	      } else {
	        response.getWriter().print("{\"status\": \"Failure\", \"message\": \"Unable to update the password.\"}");
	      }
	      conn.close();
	    } catch (SQLException ex) {
	    	
	    }
	
	}

}
