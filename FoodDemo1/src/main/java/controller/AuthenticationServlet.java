package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import model.Response;
import view.DbConnection;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		 response.setContentType("application/json");
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    System.out.println("Username: " + username);
	    System.out.println("Password: " + password);
	    boolean check=false;
	    try {
			Connection con = DbConnection.init();
			PreparedStatement st = con.prepareStatement("SELECT * FROM members where uname = ? and password=?");
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				check=true;
				
			Gson gson = new Gson();
		
			String jsonResponse = gson.toJson(new Response("Success", username));
			out.println(jsonResponse);
			System.out.print(jsonResponse);
		}
		
			
	    }catch (SQLException se) {

			se.printStackTrace();
		}
	    if(!check)
	    {
	    	Gson gson = new Gson();
	    	String jsonResponse = gson.toJson(new Response("Failure", username));
			out.println(jsonResponse);
			System.out.print(jsonResponse);
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		PrintWriter out=response.getWriter();
	    BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    JSONObject requestBody = new JSONObject(sb.toString());
	    String firstname = requestBody.getString("firstname");
	    String lastname = requestBody.getString("lastname");
	    String email = requestBody.getString("email");
	    String username = requestBody.getString("username");
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
	
	try {
		Connection con = DbConnection.init();
		PreparedStatement st = con.prepareStatement("insert into members(first_name, last_name, email, uname, password) values ('"
						+ firstname + "','" + lastname + "','" + email + "','" + username + "','" + password + "')");
		st.executeUpdate();
		out.println(jsonResponse);

	} catch (SQLException se) {

		se.printStackTrace();
	} catch (Exception e) {

		e.printStackTrace();
	}

	}
}
